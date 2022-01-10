package com.mycompany.shopping.service.dto;

import com.mycompany.shopping.domain.Product;
import com.mycompany.shopping.domain.ShoppingSession;
import lombok.Data;

@Data
public class CartItemDto {

    private Long id;

    private ShoppingSession shoppingSession;

    private Long quantity;

    private Product product;
}
