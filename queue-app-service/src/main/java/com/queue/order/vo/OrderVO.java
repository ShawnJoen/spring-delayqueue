package com.queue.order.vo;

import java.io.Serializable;
import java.util.Date;

public class OrderVO implements Serializable {
	
	private static final long serialVersionUID = -2443925280636977778L;
	private String orderTransactionNo;//交易流水号26位
	private String productName;
	private String status;
	private String createTime;
	
	public String getOrderTransactionNo() {
		return orderTransactionNo;
	}
	public void setOrderTransactionNo(String orderTransactionNo) {
		this.orderTransactionNo = orderTransactionNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "OrderVO [orderTransactionNo=" + orderTransactionNo + ", productName=" + productName + ", status="
				+ status + ", createTime=" + createTime + "]";
	}
}