package com.mycompany.shopping.service.Impl;

import com.mycompany.shopping.service.ShoppingSessionService;
import com.mycompany.shopping.service.dto.ShoppingSessionDto;

import java.util.List;
import java.util.Optional;

public class ShoppingSessionServiceImpl implements ShoppingSessionService {
    @Override
    public Optional<ShoppingSessionDto> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public List<ShoppingSessionDto> findAll() {
        return null;
    }

    @Override
    public ShoppingSessionDto save(ShoppingSessionDto shoppingSessionDto) {
        return null;
    }

    @Override
    public Optional<ShoppingSessionDto> partialUpdate(ShoppingSessionDto shoppingSessionDto) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
