package com.queue.app.orderpolling;

import java.util.concurrent.DelayQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.queue.app.orderpolling.core.OrderPollingPersist;
import com.queue.app.orderpolling.core.OrderPollingTask;

public class App {
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	public static DelayQueue<OrderPollingTask> tasks = new DelayQueue<OrderPollingTask>();
    private static ClassPathXmlApplicationContext context;
    private static ThreadPoolTaskExecutor threadPool;
    public static OrderPollingPersist orderPollingPersist;

    public static void main( String[] args ) {
    	
    	context = new ClassPathXmlApplicationContext(new String[] {"config/spring-context.xml"}); 
    	context.start();
        threadPool = (ThreadPoolTaskExecutor) context.getBean("threadPool");
        orderPollingPersist = (OrderPollingPersist) context.getBean("orderPollingPersist");
        
    	startThread(); // 启动任务处理线程
    	
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
    
    private static void startThread() {
        threadPool.execute(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(150);//0.15秒
                        logger.info("当前活动线程: {}", threadPool.getActiveCount());
                        logger.info("最大线程: {}", threadPool.getMaxPoolSize());
                        if (threadPool.getActiveCount() < threadPool.getMaxPoolSize()) {//当前活动线程 小于最大线程
                        	logger.info("@tasks.size(): {}", tasks.size());
                            final OrderPollingTask task = tasks.take();//获取过期任务(可处理的), 如果获取不到就一直等待
                            if (task != null) {
                                threadPool.execute(new Runnable() {
                                    public void run() {
                                        tasks.remove(task);
                                        task.run(); // 执行通知处理
                                        logger.info("tasks.size(): {}", tasks.size());
                                    }
                                });
                            }
                        }
                    }
                } catch (Exception e) {
                	logger.error("系统异常;", e);
                }
            }
        });
    }
}