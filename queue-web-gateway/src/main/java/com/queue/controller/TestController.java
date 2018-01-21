package com.queue.controller;

import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.queue.order.service.OrderService;
import com.queue.utils.Common;

@RestController
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private OrderService orderService;
	
	/**
	 * 平台订单创建接口(已支付):
	 * http://127.0.0.1:8080/queue-web-gateway/order
	 */
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public Map<String, Object> order(Locale locale, Model model) {

		return orderService.createOrder();
	}
	
	/**
	 * 平台异步通知订单结果接口(接收异步消息后判断远程银行订单支付状态 如成功/失败时 修改本地订单状态):
	 * http://127.0.0.1:8080/queue-web-gateway/order-notify?orderTransactionNo=
	 */
	@RequestMapping(value = "/order-notify", method = RequestMethod.GET)
	public Map<String, Object> orderNotify(Locale locale, Model model) {

		return Common.output("1", "WAITING_PAYMENT", "等待支付");
	}
	
	/**
	 * 平台订单详情接口:
	 * http://127.0.0.1:8080/queue-web-gateway/query?orderId=
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public Map<String, Object> query(@RequestParam("orderId") Long orderId, Locale locale, Model model) {
		
		return orderService.getOrderById(orderId);
	}
	
	/**
	 * 远程银行查询订单状态接口 演示地址:
	 * http://127.0.0.1:8080/queue-web-gateway/bank-query?orderTransactionNo=
	 */
	@RequestMapping(value = "/bank-query", method = RequestMethod.GET)
	public Map<String, Object> bankQuery(Locale locale, Model model) {
		
		return Common.output("1", "WAITING_PAYMENT", "等待支付");
		//return Common.output("2", "FAILED", "支付失败");
		//return Common.output("0", "SUCCESS", "支付成功");
	}
}