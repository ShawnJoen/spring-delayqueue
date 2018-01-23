package com.queue.app.notify.message;

import javax.jms.*;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class CustomThreadMessageListener implements MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(CustomThreadMessageListener.class);

	public void onMessage(Message message) {
		
		try {
			ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
	        final String jsonString = msg.getText();

//	        JSON jsonData = (JSON)JSONObject.parse(jsonString);
//	        NotifyRecord notifyRecord = JSONObject.toJavaObject(jsonData, NotifyRecord.class);
//            if (notifyRecord == null) {
//                return;
//            }
//            
	        System.out.println(jsonString);
	        
        } catch (JMSException e) {
            e.printStackTrace();
        }
	}
}