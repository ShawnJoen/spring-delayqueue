package com.queue.notify.dto;

import java.io.Serializable;
import java.util.Date;

public class NotifyRecord implements Serializable {
	private static final long serialVersionUID = 9041484953450585661L;
	private Long id;
	private Date createTime;
	private Date editTime;
	private int notifyTimes;
	private int notifyLimitTimes;
	private String url;
	private String orderTransactionNo;
	private String status;
	private String notifyRule;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getEditTime() {
		return editTime;
	}
	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}
	public int getNotifyTimes() {
		return notifyTimes;
	}
	public void setNotifyTimes(int notifyTimes) {
		this.notifyTimes = notifyTimes;
	}
	public int getNotifyLimitTimes() {
		return notifyLimitTimes;
	}
	public void setNotifyLimitTimes(int notifyLimitTimes) {
		this.notifyLimitTimes = notifyLimitTimes;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOrderTransactionNo() {
		return orderTransactionNo;
	}
	public void setOrderTransactionNo(String orderTransactionNo) {
		this.orderTransactionNo = orderTransactionNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNotifyRule() {
		return notifyRule;
	}
	public void setNotifyRule(String notifyRule) {
		this.notifyRule = notifyRule;
	}
	@Override
	public String toString() {
		return "NotifyRecord [id=" + id + ", createTime=" + createTime + ", editTime=" + editTime + ", notifyTimes="
				+ notifyTimes + ", notifyLimitTimes=" + notifyLimitTimes + ", url=" + url + ", orderTransactionNo="
				+ orderTransactionNo + ", status=" + status + ", notifyRule=" + notifyRule + "]";
	}
}