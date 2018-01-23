package com.queue.utils.redis.id;

public interface IdGenerator {

	/**
	 * @param tab
	 * @param shardId 用户编号等
	 * @return
	 */
	Long getId(String tab, long shardId);

	Long getOrderId();
	
	Long getOrderPollingId();
}