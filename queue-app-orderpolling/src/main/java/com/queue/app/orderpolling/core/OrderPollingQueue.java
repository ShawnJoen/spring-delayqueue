package com.queue.app.orderpolling.core;

import java.io.Serializable;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.queue.app.orderpolling.App;
import com.queue.app.orderpolling.entity.NotifyParam;
import com.queue.orderpolling.vo.OrderPollingRecord;
import com.queue.utils.Common;

@Component
public class OrderPollingQueue implements Serializable {
	private static final long serialVersionUID = 892095247304401593L;
	private static final Logger logger = LoggerFactory.getLogger(OrderPollingQueue.class);

    @Autowired
    private NotifyParam notifyParam;

    /**
     * 传过来的对象加到延时队列
     */
    public void addTaskDelayQueue(OrderPollingRecord orderPollingRecord) {
    	
        if (orderPollingRecord == null) {
            return;
        }
        
        logger.info("传过来的对象加到延时队列: {}", orderPollingRecord);

        if (orderPollingRecord.getPollingTimes() == 0) {
        	orderPollingRecord.setLastPollingTime(new Date());//第一次加入设置当前时间
        }else{
        	orderPollingRecord.setLastPollingTime(orderPollingRecord.getEditTime());//非第一次取上一次加入(修改)时间
        }

        if (orderPollingRecord.getPollingTimes() < orderPollingRecord.getPollingLimitTimes()) {
            //未超过最大通知次数, 继续下一次通知

            Integer nextPollingTimeInterval = orderPollingRecord.getPollingRuleMap().get(
            			String.valueOf(orderPollingRecord.getPollingTimes() + 1)
            		);//当前发送次数对应的时间间隔数
            
            orderPollingRecord.setPollingExecuteTime(
            			(nextPollingTimeInterval == null ? 0 : nextPollingTimeInterval * notifyParam.getMilliSecond())//延时毫秒数
            			 + orderPollingRecord.getLastPollingTime().getTime()//+最后通知时间
            		);

        	logger.info("轮询编号: {}, 上次通知时间: {}", orderPollingRecord.getId(), Common.getDateTimeNow(orderPollingRecord.getLastPollingTime()));
            App.tasks.put(new OrderPollingTask(orderPollingRecord));
            logger.info("轮询编号: {}, 任务添加成功后,当前队列大小: {}", orderPollingRecord.getId(), App.tasks.size());
        }
    }
}