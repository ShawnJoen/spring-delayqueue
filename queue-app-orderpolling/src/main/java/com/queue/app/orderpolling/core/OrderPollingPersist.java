package com.queue.app.orderpolling.core;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.queue.enums.NotifyStatusEnum;
import com.queue.order.service.OrderService;
import com.queue.orderpolling.service.OrderPollingService;
import com.queue.orderpolling.vo.OrderPollingRecord;
import com.queue.orderpolling.vo.OrderQueryResult;

@Service("orderPollingPersist")
public class OrderPollingPersist {
	private static final Logger logger = LoggerFactory.getLogger(OrderPollingPersist.class);
	
	@Autowired
	private OrderService orderService;
	
    @Autowired
    private OrderPollingQueue orderPollingQueue;
    
	@Autowired
	OrderPollingService orderPollingService;
	
    /**
     * 创建轮询记录
     */
    public void insertOrderPollingRecord(OrderPollingRecord orderPollingRecord) {
        orderPollingService.insertOrderPollingRecord(orderPollingRecord);
    }
    
    /**
     * 更新轮询记录
     */
    public void updateOrderPollingRecordByOrderTransactionNo(OrderPollingRecord orderPollingRecord) {
    	orderPollingService.updateOrderPollingRecordByOrderTransactionNo(orderPollingRecord);
	}
    
    /**
     * 获取订单结果
     */
    @Transactional(rollbackFor = Exception.class)
    public void getOrderResult(OrderPollingRecord orderPollingRecord){
    	
		Date pollingTime = new Date();//本次通知的时间
		
		logger.info("执行轮询时间: {}, 轮询编号: {}", pollingTime, orderPollingRecord.getId());
		
		orderPollingRecord.setEditTime(pollingTime);//取本次通知时间作为最后修改时间
		orderPollingRecord.setPollingTimes(orderPollingRecord.getPollingTimes() + 1);//通知次数 + 1

		OrderQueryResult orderQueryResult = orderService.completeTrade(orderPollingRecord.getBankTypeCode(), orderPollingRecord.getOrderTransactionNo());
		logger.info("订单轮询结果: {}", orderQueryResult);
		
		if (orderQueryResult.isStopFlag()) {
			//订单结束(不再轮询通知)
			orderPollingRecord.setStatus(orderQueryResult.getOrderStatusEnum().name());//上游(银行)返回的支付状态
			updateOrderPollingRecordByOrderTransactionNo(orderPollingRecord);
			return;
		}
		
		if (orderPollingRecord.getPollingTimes() < orderPollingRecord.getPollingLimitTimes()) {
			//判断是否超过重发次数, 未超重发次数的, 再次进入延迟发送队列
			orderPollingQueue.addTaskDelayQueue(orderPollingRecord);
			orderPollingRecord.setStatus(NotifyStatusEnum.HTTP_REQUEST_SUCCESS.name());
			logger.info("判断是否超过重发次数, 未超重发次数的, 再次进入延迟发送队列:");
		} else {
			//到达最大通知次数限制, 标记为通知失败(不再轮询通知)
			orderPollingRecord.setStatus(NotifyStatusEnum.FAILED.name());
			logger.info("订单轮询结束(订单处理失败):");
		}
		
		updateOrderPollingRecordByOrderTransactionNo(orderPollingRecord);
    }
}