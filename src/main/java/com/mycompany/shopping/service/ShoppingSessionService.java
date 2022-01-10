package com.mycompany.shopping.service;

import com.mycompany.shopping.service.dto.ShoppingSessionDto;

import java.util.List;
import java.util.Optional;

public interface ShoppingSessionService {

    Optional<ShoppingSessionDto> findOne(Long id);

    List<ShoppingSessionDto> findAll();

    ShoppingSessionDto save(ShoppingSessionDto shoppingSessionDto);

    Optional<ShoppingSessionDto> partialUpdate(ShoppingSessionDto shoppingSessionDto);

    void delete(Long id);
}
