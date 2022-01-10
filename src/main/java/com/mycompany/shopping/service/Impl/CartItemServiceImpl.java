package com.mycompany.shopping.service.Impl;

import com.mycompany.shopping.service.CartItemService;
import com.mycompany.shopping.service.dto.CartItemDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Override
    public Optional<CartItemDto> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public List<CartItemDto> findAll() {
        return null;
    }

    @Override
    public CartItemDto save(CartItemDto cartItemDto) {
        return null;
    }

    @Override
    public Optional<CartItemDto> partialUpdate(CartItemDto cartItemDto) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
