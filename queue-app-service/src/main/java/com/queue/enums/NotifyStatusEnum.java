package com.queue.enums;

/**
 * Enum直接输出Value
 * 		.name()输出Key
 */
public enum NotifyStatusEnum {

	CREATED("通知记录已创建"),
    SUCCESS("通知成功"),
    FAILED("通知失败"),
    HTTP_REQUEST_SUCCESS("http请求响应成功"),
    HTTP_REQUEST_FALIED("http请求失败");
	
	private String desc;
	private NotifyStatusEnum(String desc) {
		this.desc = desc;
	}
	
    public String getDesc() {
        return desc;
    }
    
	public String toString() {
		return this.desc;
	}
}