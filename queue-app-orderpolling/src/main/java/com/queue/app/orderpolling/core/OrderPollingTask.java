package com.queue.app.orderpolling.core;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderPollingTask implements Runnable, Delayed {
	private static final Logger logger = LoggerFactory.getLogger(OrderPollingTask.class);
	@Override
	public int compareTo(Delayed o) {

		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {

		return 0;
	}

	@Override
	public void run() {


	}

}