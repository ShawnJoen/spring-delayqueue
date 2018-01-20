package com.queue.controller;

import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.queue.order.service.MessageSendService;
import com.queue.order.vo.OrderVO;
import com.queue.utils.Common;

@RestController
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private MessageSendService messageSendService;
	
	//http://127.0.0.1:8080/queue-web-gateway/order?orderId=
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public Map<String, Object> query(Locale locale, Model model) {
		
		return Common.output("1", "WAIT_PAY", "等待支付");
		//return Common.output("2", "FAILED", "支付失败");
		//return Common.output("0", "SUCCESS", "支付成功");
	}
	
	//http://127.0.0.1:8080/queue-web-gateway/order
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public Map<String, Object> order(Locale locale, Model model) {
		
		OrderVO orderVO = new OrderVO();
		orderVO.setOrderTransactionNo(Common.createOrderTransactionNo());
		orderVO.setProductName("测试商品");
		orderVO.setStatus("CREATED");
		orderVO.setCreateTime(Common.getDateTimeNow());
		
		//发消息 在 queue-app-notify.jar轮询判断订单状态
		messageSendService.sendMessage(orderVO);
		
		return Common.output("0", orderVO, "下单成功");
	}
}