package com.queue.enums;

/**
 * Enum直接输出Value
 * 		.name()输出Key
 */
public enum OrderStatusEnum {

	SUCCESS("支付成功"),
	FAILED("支付失败"),
	WAITING_PAYMENT("等待支付"),
	NOTPAY("未支付"),
	UNKNOWN("未知结果");
	
	//NOT_COMPLETE_SETT("未结算"),
	//CANCELED("已撤销");
	
	private String desc;
	private OrderStatusEnum(String desc) {
		this.desc = desc;
	}
	
    public String getDesc() {
        return desc;
    }
    
	public String toString() {
		return this.desc;
	}
}