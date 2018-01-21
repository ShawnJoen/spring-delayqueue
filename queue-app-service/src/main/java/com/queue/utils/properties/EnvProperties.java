package com.queue.utils.properties;

import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置环境文件 
 */
public class EnvProperties {
	private static final Logger logger = LoggerFactory.getLogger(EnvProperties.class);
	
	private static Properties properties = new Properties();
	
	static {
		try {
			properties.load(EnvProperties.class.getClassLoader()
					.getResourceAsStream("config/env.properties"));
		} catch (IOException e) {
			logger.error("EnvProperties init error", e);
		}
	}
	
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	public static String getProperty(String key, String value) {
		return properties.getProperty(key, value);
	}
}