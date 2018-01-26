package com.queue.app.orderpolling.core;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.queue.app.orderpolling.App;
import com.queue.orderpolling.vo.OrderPollingRecord;

public class OrderPollingTask implements Runnable, Delayed {
	private static final Logger logger = LoggerFactory.getLogger(OrderPollingTask.class);
	
    private OrderPollingPersist orderPollingPersist = App.orderPollingPersist;
	
	private long executeTime;

	private OrderPollingRecord orderPollingRecord;
	
	public OrderPollingTask() {}
	
	public OrderPollingTask(OrderPollingRecord orderPollingRecord) {
		this.orderPollingRecord = orderPollingRecord;
		this.executeTime = orderPollingRecord.getPollingExecuteTime();
	}
	
	/**
	 * 比较当前时间(task.executeTime)与任务允许执行的开始时间(executeTime)
	 * 如果当前时间到了或超过任务允许执行的开始时间,那么就返回-1,可以执行
	 */
	@Override
	public int compareTo(Delayed o) {
		OrderPollingTask task = (OrderPollingTask) o;
		return executeTime > task.executeTime ? 1 : (executeTime < task.executeTime ? -1 : 0);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(executeTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	/**
	 * 执行轮询
	 */
	@Override
	public void run() {
		orderPollingPersist.getOrderResult(orderPollingRecord);
	}

}