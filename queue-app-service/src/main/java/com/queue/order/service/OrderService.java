package com.queue.order.service;

import java.util.Map;

public interface OrderService {

	Map<String, Object> createOrder();
	Map<String, Object> getOrderById(final Long orderId);
}