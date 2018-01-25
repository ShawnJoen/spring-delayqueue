package com.queue.order.service;

import java.util.Map;
import com.queue.order.dto.Order;
import com.queue.orderpolling.vo.OrderQueryResult;

public interface OrderService {

	Map<String, Object> createOrder();
	void updateOrderByOrderTransactionNo(Order order);
	Map<String, Object> getOrderById(Long orderId);
	OrderQueryResult completeTrade(String bankTypeCode, String orderTransactionNo);
}