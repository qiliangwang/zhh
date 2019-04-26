package com.iceberg.converter;

import com.iceberg.dto.OrderDTO;
import com.iceberg.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangql
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setOrderDetailList(orderForm.getItems());

        return orderDTO;
    }
}
