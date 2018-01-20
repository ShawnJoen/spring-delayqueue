package com.queue.app.notify;

import javax.jms.*;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.queue.order.vo.OrderVO;

public class CustomThreadMessageListener implements MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomThreadMessageListener.class);

	public void onMessage(Message message) {
		
		try {
			ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
	        final String jsonString = msg.getText();

	        JSON jsonData = (JSON)JSONObject.parse(jsonString);
	        OrderVO orderVO = JSONObject.toJavaObject(jsonData, OrderVO.class);
            if (orderVO == null) {
                return;
            }
            
	        System.out.println(orderVO);
	        
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}
}