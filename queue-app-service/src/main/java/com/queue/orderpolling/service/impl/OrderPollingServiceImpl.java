package com.queue.orderpolling.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.queue.orderpolling.dao.OrderPollingDao;
import com.queue.orderpolling.service.OrderPollingService;
import com.queue.orderpolling.vo.OrderPollingRecord;

@Service("orderPollingService")
public class OrderPollingServiceImpl implements OrderPollingService {
	
	@Autowired
	private OrderPollingDao orderPollingDao;
	
	@Transactional("transaction")
	@Override
	public void insertOrderPollingRecord(OrderPollingRecord orderPollingRecord) {
		orderPollingDao.insertOrderPollingRecord(orderPollingRecord);
	}
	
	@Transactional("transaction")
	@Override
	public void updateOrderPollingRecordByOrderTransactionNo(OrderPollingRecord orderPollingRecord) {
		orderPollingDao.updateOrderPollingRecordByOrderTransactionNo(orderPollingRecord);
	}

	@Override
	public OrderPollingRecord getOrderPollingRecordByOrderTransactionNo(String orderTransactionNo) {
		return orderPollingDao.getOrderPollingRecordByOrderTransactionNo(orderTransactionNo);
	}

}