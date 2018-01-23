package com.queue.app.orderpolling.message;

import javax.jms.*;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.queue.order.service.OrderService;
import com.queue.orderpolling.dto.OrderPollingRecord;

public class OrderPollingMessageListener implements MessageListener {
	private static final Logger logger = LoggerFactory.getLogger(OrderPollingMessageListener.class);

	@Autowired
	private OrderService orderService;
	
	public void onMessage(Message message) {
		
		try {
			ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
	        final String jsonString = msg.getText();

	        JSON jsonData = (JSON)JSONObject.parse(jsonString);
	        OrderPollingRecord orderPollingRecord = JSONObject.toJavaObject(jsonData, OrderPollingRecord.class);
            if (orderPollingRecord == null) {
                return;
            }
            
	        System.out.println(orderPollingRecord);
	        
	        //插入到 延时消息队列
			//orderService.completeTrade("WEIXINPAY_IMPL", "18012323955823755442016257");
	        
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}
}