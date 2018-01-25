package com.queue.orderpolling.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class OrderPollingRecord implements Serializable {
	private static final long serialVersionUID = 9041484953450585661L;
	
	private Long id;
	private Date createTime;
	private Date editTime;
	private int pollingTimes;
	private int pollingLimitTimes;
	private String bankTypeCode;
//	private String url;
	private String orderTransactionNo;
	private String status;
	private String pollingRule;
	
	//最后轮询时间
	private Date lastPollingTime;
	//轮询执行时间(时间戳)
	private long pollingExecuteTime;
	
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
	public int getPollingTimes() {
		return pollingTimes;
	}
	public void setPollingTimes(int pollingTimes) {
		this.pollingTimes = pollingTimes;
	}
	public int getPollingLimitTimes() {
		return pollingLimitTimes;
	}
	public void setPollingLimitTimes(int pollingLimitTimes) {
		this.pollingLimitTimes = pollingLimitTimes;
	}
	public String getBankTypeCode() {
		return bankTypeCode;
	}
	public void setBankTypeCode(String bankTypeCode) {
		this.bankTypeCode = bankTypeCode;
	}
	//	public String getUrl() {
//		return url;
//	}
//	public void setUrl(String url) {
//		this.url = url;
//	}
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
	public String getPollingRule() {
		return pollingRule;
	}
	public void setPollingRule(String pollingRule) {
		this.pollingRule = pollingRule;
	}
    /**
     * 获取通知规则的Map<String, Integer>.
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Map<String, Integer> getPollingRuleMap(){
        return (Map) JSONObject.parseObject(getPollingRule());
    }
	public Date getLastPollingTime() {
		return lastPollingTime;
	}
	public void setLastPollingTime(Date lastPollingTime) {
		this.lastPollingTime = lastPollingTime;
	}
	public long getPollingExecuteTime() {
		return pollingExecuteTime;
	}
	public void setPollingExecuteTime(long pollingExecuteTime) {
		this.pollingExecuteTime = pollingExecuteTime;
	}
	@Override
	public String toString() {
		return "OrderPollingRecord [id=" + id + ", createTime=" + createTime + ", editTime=" + editTime
				+ ", pollingTimes=" + pollingTimes + ", pollingLimitTimes=" + pollingLimitTimes
				+ ", orderTransactionNo=" + orderTransactionNo + ", status=" + status + ", pollingRule=" + pollingRule
				+ ", lastPollingTime=" + lastPollingTime + ", pollingExecuteTime=" + pollingExecuteTime + "]";
	}
}