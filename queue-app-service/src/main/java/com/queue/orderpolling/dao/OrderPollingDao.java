package com.queue.orderpolling.dao;

import com.queue.orderpolling.vo.OrderPollingRecord;

public interface OrderPollingDao {
	void insertOrderPollingRecord(OrderPollingRecord order);
	void updateOrderPollingRecordByOrderTransactionNo(OrderPollingRecord order);
	OrderPollingRecord getOrderPollingRecordByOrderTransactionNo(String orderTransactionNo);
}