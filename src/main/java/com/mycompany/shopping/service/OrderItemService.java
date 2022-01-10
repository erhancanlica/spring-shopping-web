package com.mycompany.shopping.service;

import com.mycompany.shopping.service.dto.OrderItemDto;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {

    Optional<OrderItemDto> findOne(Long id);

    List<OrderItemDto> findAll();

    OrderItemDto save(OrderItemDto orderItemDto);

    Optional<OrderItemDto> partialUpdate(OrderItemDto orderItemDto);

    void delete(Long id);
}
