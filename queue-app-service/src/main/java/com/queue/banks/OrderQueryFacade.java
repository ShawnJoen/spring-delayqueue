package com.queue.banks;

import com.queue.orderpolling.vo.OrderQueryResult;

public interface OrderQueryFacade {
	
    /**
     * 远程银行查询订单状态接口
     * @param orderTransactionNo
     * @return
     */
    public OrderQueryResult orderQuery(String orderTransactionNo);
}