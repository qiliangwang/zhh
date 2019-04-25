package com.iceberg.service;

import com.iceberg.dto.OrderDTO;

/**
 * 推送消息
 * @author wangql
 */
public interface PushMessageService {

    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
