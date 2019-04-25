package com.iceberg.service;

import com.iceberg.dto.OrderDTO;

/**
 * 买家
 * @author wangql
 */
public interface BuyerService {

    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}
