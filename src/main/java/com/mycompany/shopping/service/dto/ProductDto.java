package com.mycompany.shopping.service.dto;

import com.mycompany.shopping.domain.OrderItem;
import com.mycompany.shopping.domain.CartItem;
import com.mycompany.shopping.domain.Discount;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {

    private Long id;

    private String name;

    private String description;

    private String category;

    private BigDecimal price;

    private Discount discount;

    private OrderItem orderItem;

    private CartItem cartItem;

}
