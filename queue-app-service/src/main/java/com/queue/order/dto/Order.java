package com.queue.order.dto;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
	private static final long serialVersionUID = -2443925280636977778L;
	private Long id;//订单编号
	private String orderTransactionNo;//交易流水号26位
	private String bankTypeCode;
	private String productName;
	private String status;
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderTransactionNo() {
		return orderTransactionNo;
	}
	public void setOrderTransactionNo(String orderTransactionNo) {
		this.orderTransactionNo = orderTransactionNo;
	}
	public String getBankTypeCode() {
		return bankTypeCode;
	}
	public void setBankTypeCode(String bankTypeCode) {
		this.bankTypeCode = bankTypeCode;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", orderTransactionNo=" + orderTransactionNo + ", bankTypeCode=" + bankTypeCode
				+ ", productName=" + productName + ", status=" + status + ", createTime=" + createTime + "]";
	}
}