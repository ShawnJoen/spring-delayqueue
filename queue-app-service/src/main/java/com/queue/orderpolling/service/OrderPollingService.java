package com.queue.orderpolling.service;

import com.queue.orderpolling.vo.OrderPollingRecord;

public interface OrderPollingService {
	void insertOrderPollingRecord(OrderPollingRecord orderPollingRecord);
	void updateOrderPollingRecordByOrderTransactionNo(OrderPollingRecord orderPollingRecord);
	OrderPollingRecord getOrderPollingRecordByOrderTransactionNo(String orderTransactionNo);
}