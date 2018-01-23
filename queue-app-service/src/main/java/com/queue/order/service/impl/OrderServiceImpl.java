package com.queue.order.service.impl;

import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.queue.orderpolling.dto.OrderPollingRecord;
import com.queue.orderpolling.vo.OrderQueryResult;
import com.queue.banks.OrderQueryFacade;
import com.queue.enums.OrderStatusEnum;
import com.queue.factorys.BankFactory;
import com.queue.order.dao.OrderDao;
import com.queue.order.dto.Order;
import com.queue.order.service.OrderService;
import com.queue.utils.Common;
import com.queue.utils.message.MessageSendService;
import com.queue.utils.properties.EnvProperties;
import com.queue.utils.redis.id.IdGenerator;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private MessageSendService messageSendService;
	
	@Autowired
	private IdGenerator idGenerator;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private BankFactory bankFactory;
	
	@Transactional("transaction")
	@Override
	public Map<String, Object> createOrder() {
		
		String orderTransactionNo = Common.createOrderTransactionNo();
		Date date = new Date();
		//创建订单
		Order order = new Order();
		order.setId(idGenerator.getOrderId());
		order.setOrderTransactionNo(orderTransactionNo);
		order.setBankTypeCode("ALIPAY_IMPL");//WEIXINPAY_IMPL
		order.setProductName("测试商品");
		order.setStatus("WAITING_PAYMENT");
		order.setCreateTime(date);
		//保存到DB中
		orderDao.insertOrder(order);
		//订单创建同时 queue-app-orderpolling.jar轮询查询银行支付状态 处理本地平台订单状态
		OrderPollingRecord orderPollingRecord = new OrderPollingRecord();
		orderPollingRecord.setId(idGenerator.getOrderPollingId());
		orderPollingRecord.setCreateTime(date);
		orderPollingRecord.setUrl(EnvProperties.getProperty("bank.query.url"));
		orderPollingRecord.setOrderTransactionNo(orderTransactionNo);
		messageSendService.sendMessage(orderPollingRecord);
		
		return Common.output("0", order, 
				"订单创建同时支付(假设)并且同时 轮询查询银行支付状态后 修改平台订单状态");
	}

	@Override
	public Map<String, Object> getOrderById(final Long orderId) {
		
		Order order = orderDao.getOrderById(orderId);
		if (order == null) {
			return Common.output("1", order, "订单找不到！");
		} else {
			return Common.output("0", order, "");
		}
	}

	@Transactional("transaction")
	@Override
	public void updateOrderByOrderTransactionNo(final Order order) {

		orderDao.updateOrderByOrderTransactionNo(order);
	}
	
	/**
	 * 同步获取不到支付结果时 通过订单轮询 修改平台订单状态 并且 给通知下游
	 * 
	 * return true(轮询结束)/false(继续轮询)
	 */
	@Transactional("transaction")
	@Override
	public boolean completeTrade(final String bankTypeCode, final String orderTransactionNo) {
		logger.info("银行渠道编号: {}, 平台交易流水号: {}", bankTypeCode, orderTransactionNo);
		//根据银行渠道编号 获取指定 银行实体类 查询支付状态
		OrderQueryFacade orderQueryFacade = (OrderQueryFacade)bankFactory.getService(bankTypeCode);//WEIXINPAY_IMPL, ALIPAY_IMPL
		OrderQueryResult orderQueryResult = orderQueryFacade.orderQuery(orderTransactionNo);
		//查询平台订单信息
		Order order = orderDao.getOrderByOrderTransactionNo(orderTransactionNo);
		logger.info("平台订单当前信息: {}", order);
		if (OrderStatusEnum.SUCCESS.name().equals(order.getStatus())
				|| OrderStatusEnum.FAILED.name().equals(order.getStatus())) {//平台订单状态为 成功/失败 表示已处理订单
			logger.info("已处理过的订单 无需重复处理: {}", orderTransactionNo);
			
			return true;
		} else if (OrderStatusEnum.WAITING_PAYMENT.name().equals(order.getStatus())) {//平台订单状态为 等待支付 表示等待处理订单
			
			if (OrderStatusEnum.FAILED.name().equals(orderQueryResult.getOrderStatusEnum().name())) {//银行支付结果为 支付失败
				logger.info("支付结果为 *失败 结束订单");
				
				//修改平台订单状态
				order.setStatus(OrderStatusEnum.FAILED.name());
				updateOrderByOrderTransactionNo(order);
				//通知下游
				//messageSendService.sendMessage(ORDER_NOTIFY_QUEUE);
				return true;
			} else if (OrderStatusEnum.SUCCESS.name().equals(orderQueryResult.getOrderStatusEnum().name())) {//银行支付结果为 支付成功
				logger.info("支付结果为 *成功 结束订单");
				
				//修改平台订单状态
				order.setStatus(OrderStatusEnum.SUCCESS.name());
				updateOrderByOrderTransactionNo(order);
				//通知下游
				//messageSendService.sendMessage(ORDER_NOTIFY_QUEUE);
				return true;
			}
			
			//其他支付结果继续轮询直到轮询最大次数结束(可能会有掉单)
			logger.info("支付结果 *未知 继续轮询查询");
		}
		
		return false;
	}
}