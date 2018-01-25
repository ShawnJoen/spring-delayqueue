package com.queue.app.orderpolling.message;

import java.util.Date;
import java.util.Map;
import javax.jms.*;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.queue.app.orderpolling.core.OrderPollingPersist;
import com.queue.app.orderpolling.core.OrderPollingQueue;
import com.queue.app.orderpolling.entity.NotifyParam;
import com.queue.enums.NotifyStatusEnum;
import com.queue.orderpolling.vo.OrderPollingRecord;

public class OrderPollingMessageListener implements MessageListener {
	private static final Logger logger = LoggerFactory.getLogger(OrderPollingMessageListener.class);

	@Autowired
	private NotifyParam notifyParam;
	
    @Autowired
    private OrderPollingQueue orderPollingQueue;
	
	@Autowired
    private OrderPollingPersist orderPollingPersist;
	
	public void onMessage(Message message) {
		
		try {
			ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
	        final String jsonString = msg.getText();
	        
	        logger.info("订单轮询接收消息: {}", jsonString);

	        JSON jsonData = (JSON)JSONObject.parse(jsonString);
	        OrderPollingRecord orderPollingRecord = JSONObject.toJavaObject(jsonData, OrderPollingRecord.class);
            if (orderPollingRecord == null) {
                return;
            }
            
//    		orderPollingRecord.setId(idGenerator.getOrderPollingId());
//    		orderPollingRecord.setCreateTime(date);
//    		orderPollingRecord.setOrderTransactionNo(orderTransactionNo);

	        //插入到 延时消息队列
            orderPollingRecord.setEditTime(new Date());
            orderPollingRecord.setStatus(NotifyStatusEnum.CREATED.name());
            orderPollingRecord.setPollingTimes(0);
            orderPollingRecord.setPollingLimitTimes(notifyParam.getMaxNotifyTimes());
            Map<Integer, Integer> notifyParams = notifyParam.getNotifyParams();
            orderPollingRecord.setPollingRule(JSONObject.toJSONString(notifyParams));
            orderPollingPersist.insertOrderPollingRecord(orderPollingRecord);
            //添加到延时队列等待轮询处理
            orderPollingQueue.addTaskDelayQueue(orderPollingRecord);
	        
        } catch (JMSException e) {
        	logger.error("订单轮询接收消息错误 1: {}", e);
        } catch (Exception e) {
        	logger.error("订单轮询接收消息错误 2: {}", e);
        }
	}
}