package com.queue.factorys;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

@Component("bankFactory")
public class BankFactory implements BeanFactoryAware {
	
	private BeanFactory beanFactory;
	
	/**
	 * 根据支付通道编码获取支付具体实体类
	 */
	public Object getService(String payCode) {
		return beanFactory.getBean(payCode);
	}
	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
}