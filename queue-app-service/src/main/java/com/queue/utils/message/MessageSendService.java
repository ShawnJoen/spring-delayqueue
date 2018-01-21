package com.queue.utils.message;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

@Component("messageSendService")
public class MessageSendService {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
    private Destination queueKEY;

    public void sendMessage(final Object data) {
    	
    	Object JsonData = JSONObject.toJSON(data);
    	final String jsonString = JsonData.toString();

    	//发送消息
        jmsTemplate.send(queueKEY, new MessageCreator() {
        	public Message createMessage(Session session) throws JMSException {
                 return session.createTextMessage(jsonString);
            }
        });
    }
}