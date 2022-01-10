package com.mycompany.shopping.service.dto;

import com.mycompany.shopping.domain.CartItem;
import com.mycompany.shopping.domain.User;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ShoppingSessionDto {

    private Long id;

    private User user;

    private Float total;

    private Set<CartItem> cartItems = new HashSet<>();
}
