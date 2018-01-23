package com.queue.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.queue.order.service.OrderService;

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
	 * 平台订单详情接口:
	 * http://127.0.0.1:8080/queue-web-gateway/query?orderId=
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public Map<String, Object> query(@RequestParam(value="orderId", required=false, defaultValue="0") Long orderId, 
			Locale locale, Model model) {

		return orderService.getOrderById(orderId);
	}
	
	/**
	 * 平台异步通知订单结果接口(银行异步回调返回支付结果):
	 * http://127.0.0.1:8080/queue-web-gateway/orderNotify
	 */
	@RequestMapping(value = "/orderNotify", method = RequestMethod.GET)
	public Map<String, Object> orderNotify(Locale locale, Model model) {
		return null;
	}

	/**
	 * 远程银行查询订单状态接口(假设) 演示地址:
	 * http://127.0.0.1:8080/queue-web-gateway/bankQuery?orderTransactionNo=
	 */
	@RequestMapping(value = "/bankQuery", method = {RequestMethod.GET, RequestMethod.POST})
	public Map<String, String> bankQuery(@ModelAttribute("orderTransactionNo") String orderTransactionNo,
			Locale locale, Model model) {
		logger.info("银行(假设) 平台交易流水号: {}", orderTransactionNo);
		
		Map<String, String> result = new HashMap();
		result.put("status", "SUCCESS"); result.put("message", "支付成功");
		//result.put("status", "FAILED"); result.put("message", "支付失败");
		//result.put("status", "NOTPAY"); result.put("message", "未支付");
		
		return result;
	}
}