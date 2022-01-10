package com.mycompany.shopping.service;

import com.mycompany.shopping.service.dto.CartItemDto;

import java.util.List;
import java.util.Optional;

public interface CartItemService {

    Optional<CartItemDto> findOne(Long id);

    List<CartItemDto> findAll();

    CartItemDto save(CartItemDto cartItemDto);

    Optional<CartItemDto> partialUpdate(CartItemDto cartItemDto);

    void delete(Long id);
}
