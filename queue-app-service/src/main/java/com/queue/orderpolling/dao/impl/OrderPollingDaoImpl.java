package com.queue.orderpolling.dao.impl;

import org.springframework.stereotype.Repository;
import com.queue.BaseDaoImpl;
import com.queue.orderpolling.dao.OrderPollingDao;
import com.queue.orderpolling.vo.OrderPollingRecord;

@Repository
public class OrderPollingDaoImpl extends BaseDaoImpl implements OrderPollingDao {

	@Override
	public void insertOrderPollingRecord(OrderPollingRecord orderPollingRecord) {
		super.getSqlSession().insert(super.getStatement("insertOrderPollingRecord"), orderPollingRecord);
	}

	@Override
	public void updateOrderPollingRecordByOrderTransactionNo(OrderPollingRecord orderPollingRecord) {
		super.getSqlSession().update(super.getStatement("updateOrderPollingRecordByOrderTransactionNo"), orderPollingRecord);
	}

	@Override
	public OrderPollingRecord getOrderPollingRecordByOrderTransactionNo(String orderTransactionNo) {
		return super.getSqlSession().selectOne(super.getStatement("getOrderPollingRecordByOrderTransactionNo"), orderTransactionNo);
	}
}