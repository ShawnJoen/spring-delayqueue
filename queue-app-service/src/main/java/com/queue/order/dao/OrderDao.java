package com.queue.order.dao;

import com.queue.order.dto.Order;

public interface OrderDao {
	
	void insertOrder(Order order);
	void updateOrderByOrderTransactionNo(Order order);
	Order getOrderById(Long orderId);
	Order getOrderByOrderTransactionNo(String orderTransactionNo);
}