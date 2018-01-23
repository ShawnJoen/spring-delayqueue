package com.queue.order.dao.impl;

import org.springframework.stereotype.Repository;
import com.queue.BaseDaoImpl;
import com.queue.order.dao.OrderDao;
import com.queue.order.dto.Order;

@Repository
public class OrderDaoImpl extends BaseDaoImpl implements OrderDao {
	
	@Override
	public void insertOrder(final Order order) {
		super.getSqlSession().insert(super.getStatement("insertOrder"), order);
	}

	@Override
	public void updateOrderByOrderTransactionNo(final Order order) {
		super.getSqlSession().update(super.getStatement("updateOrderByOrderTransactionNo"), order);
	}
	
	@Override
	public Order getOrderById(final Long orderId) {
		return super.getSqlSession().selectOne(super.getStatement("getOrderById"), orderId);
	}

	@Override
	public Order getOrderByOrderTransactionNo(final String orderTransactionNo) {
		return super.getSqlSession().selectOne(super.getStatement("getOrderByOrderTransactionNo"), orderTransactionNo);
	}
}