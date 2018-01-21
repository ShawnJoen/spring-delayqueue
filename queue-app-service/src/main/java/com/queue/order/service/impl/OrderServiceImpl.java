package com.queue.order.service.impl;

import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.queue.notify.dto.NotifyRecord;
import com.queue.order.dao.OrderDao;
import com.queue.order.dto.Order;
import com.queue.order.service.OrderService;
import com.queue.utils.Common;
import com.queue.utils.message.MessageSendService;
import com.queue.utils.properties.EnvProperties;
import com.queue.utils.redis.id.IdGenerator;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {

	@Autowired
	private MessageSendService messageSendService;
	
	@Autowired
	private IdGenerator idGenerator;
	
	@Autowired
	private OrderDao orderDao;
	
	@Transactional("transaction")
	@Override
	public Map<String, Object> createOrder() {
		
		String orderTransactionNo = Common.createOrderTransactionNo();
		Date date = new Date();
		//创建订单
		Order order = new Order();
		order.setId(idGenerator.getOrderId());
		order.setOrderTransactionNo(orderTransactionNo);
		order.setProductName("测试商品");
		order.setStatus("WAITING_PAYMENT");
		order.setCreateTime(date);
		//保存到DB中
		orderDao.insertOrder(order);
		//发消息 在 queue-app-notify.jar轮询判断订单状态
		NotifyRecord notifyRecord = new NotifyRecord();
		notifyRecord.setId(idGenerator.getNotifyId());
		notifyRecord.setCreateTime(date);
		notifyRecord.setUrl(EnvProperties.getProperty("bank.query.url"));
		notifyRecord.setOrderTransactionNo(orderTransactionNo);
		messageSendService.sendMessage(notifyRecord);
		
		return Common.output("0", order, 
				"订单创建同时支付(假设), 平台异步轮询 远程查询支付状态修改本地订单状态");
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
	
}