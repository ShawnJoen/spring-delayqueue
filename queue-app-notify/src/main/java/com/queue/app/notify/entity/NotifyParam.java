package com.queue.app.notify.entity;

import java.util.Map;

public class NotifyParam {

    /**
     * 通知规则(通知延时时间)
     */
    private Map<Integer, Integer> notifyParams;

    public Map<Integer, Integer> getNotifyParams() {
        return notifyParams;
    }

    public void setNotifyParams(Map<Integer, Integer> notifyParams) {
        this.notifyParams = notifyParams;
    }

    /**
     * 最大通知次数
     */
    public Integer getMaxNotifyTimes() {
        return notifyParams == null ? 0 : notifyParams.size();
    }
}