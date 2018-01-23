package com.queue.app.notify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
    private static ClassPathXmlApplicationContext context;

    public static void main( String[] args ) {
    	
    	context = new ClassPathXmlApplicationContext(new String[] {"config/spring-context.xml"}); 
    	context.start();
    }
}