package com.queue.order.dao;

import com.queue.order.dto.Order;

public interface OrderDao {
	
	void insertOrder(final Order order);
	Order getOrderById(final Long orderId);
}