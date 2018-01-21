package com.queue.utils.redis.id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.queue.utils.properties.EnvProperties;

@Component("idGenerator")
public class IdGeneratorImpl implements IdGenerator {
	private static final Logger logger = LoggerFactory.getLogger(IdGeneratorImpl.class);
	
	private static IdGeneratorUtil idGenerator = null;

	static {
		idGenerator = IdGeneratorUtil.builder()
			.addHost(EnvProperties.getProperty("redis.id.generator.master.ip"),
				Integer.parseInt(EnvProperties.getProperty("redis.id.generator.master.port")),
				EnvProperties.getProperty("redis.id.generator.master.password"),
				EnvProperties.getProperty("redis.id.generator.master.luasha"))
//			.addHost(ip, port, password, luasha)
		.build();
	}

	@Override
	public Long getId(String tab, long shardId) {
		try {
			long id = idGenerator.next(tab, shardId);
			return id;//19位 如:6360753746048472067
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Long getOrderId() {
		return getId("order", 123456789);
	}
	
	@Override
	public Long getNotifyId() {
		return getId("notify", 123456789);
	}
}