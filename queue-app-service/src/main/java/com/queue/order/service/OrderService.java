package com.queue.order.service;

import java.util.Map;
import com.queue.order.dto.Order;

public interface OrderService {

	Map<String, Object> createOrder();
	void updateOrderByOrderTransactionNo(Order order);
	Map<String, Object> getOrderById(Long orderId);
	boolean completeTrade(String bankTypeCode, String orderTransactionNo);
}