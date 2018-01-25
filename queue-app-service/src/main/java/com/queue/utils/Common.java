package com.queue.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.toolkit.IdWorker;

public class Common {
	private static final Logger logger = LoggerFactory.getLogger(Common.class);
	
	static final String DATEFORMAT_YYYY_MM_DDHH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	static final String DATEFORMAT_YYMMDDHH = "yyMMddHH";
	 
	final public static Map<String, Object> output(String code, Object object, String message) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("code", code);
		map.put("data", object);
		map.put("message", message);
		return map;
	}
	
	final public static String getDateTimeNow() {
		
		SimpleDateFormat sdf = new SimpleDateFormat(Common.DATEFORMAT_YYYY_MM_DDHH_MM_SS);
		return sdf.format(new Date());
	}
	
	final public static String getDateTimeNow(Date data) {
		
		SimpleDateFormat sdf = new SimpleDateFormat(Common.DATEFORMAT_YYYY_MM_DDHH_MM_SS);
		return sdf.format(data);
	}
	
	final public static String getOrderIdDateTimeNow() {
		
		SimpleDateFormat sdf = new SimpleDateFormat(Common.DATEFORMAT_YYMMDDHH);
		return sdf.format(new Date());
	}
	
	final public static String createOrderTransactionNo() {
		try {

			String seq = String.valueOf(IdWorker.getId());	//18位 如:954720955760914433
			return getOrderIdDateTimeNow() + seq;			//26位 如:18012022954720955760914433
		} catch (Exception e) {
			logger.error("生成交易流水号失败:", e);
		}
		
		return null;
	}
}