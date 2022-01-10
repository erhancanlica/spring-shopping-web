package com.mycompany.shopping.service.dto;

import com.mycompany.shopping.domain.OrderDetail;
import com.mycompany.shopping.domain.Product;
import lombok.Data;

@Data
public class OrderItemDto {

    private String id;

    private OrderDetail orderDetail;

    private Product product;
}
