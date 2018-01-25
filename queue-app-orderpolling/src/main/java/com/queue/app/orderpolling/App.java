package com.queue.app.orderpolling;

import java.util.concurrent.DelayQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.queue.app.orderpolling.core.OrderPollingPersist;
import com.queue.app.orderpolling.core.OrderPollingTask;
import com.queue.orderpolling.service.OrderPollingService;

public class App {
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	public static DelayQueue<OrderPollingTask> tasks = new DelayQueue<OrderPollingTask>();
    private static ClassPathXmlApplicationContext context;
    //private static ThreadPoolTaskExecutor threadPool;
    //public static OrderPollingService orderPollingService;
    //public static OrderPollingPersist orderPollingPersist;

    public static void main( String[] args ) {
    	
    	context = new ClassPathXmlApplicationContext(new String[] {"config/spring-context.xml"}); 
    	context.start();
        //threadPool = (ThreadPoolTaskExecutor) context.getBean("threadPool");
        //orderPollingService = (OrderPollingService) context.getBean("orderPollingService");
        //orderPollingPersist = (OrderPollingPersist) context.getBean("orderPollingPersist");
        
        synchronized (App.class) {
            while (true) {
                try {
                    App.class.wait();
                } catch (InterruptedException e) {
                	logger.error("synchronized error: {}", e);
                }
            }
        }
    }
}