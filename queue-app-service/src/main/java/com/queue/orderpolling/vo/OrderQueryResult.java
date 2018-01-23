package com.queue.orderpolling.vo;

import java.io.Serializable;
import com.queue.enums.OrderStatusEnum;

public class OrderQueryResult implements Serializable {
	private static final long serialVersionUID = -8050848933681102649L;

	/**
	 * Enum直接输出Value
	 * 		.name()输出Key
	 */
	private OrderStatusEnum orderStatusEnum;

	public OrderStatusEnum getOrderStatusEnum() {
		return orderStatusEnum;
	}

	public void setOrderStatusEnum(OrderStatusEnum orderStatusEnum) {
		this.orderStatusEnum = orderStatusEnum;
	}
}