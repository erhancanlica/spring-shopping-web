package com.mycompany.shopping.service.Impl;

import com.mycompany.shopping.service.dto.OrderItemDto;
import com.mycompany.shopping.service.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Override
    public Optional<OrderItemDto> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public List<OrderItemDto> findAll() {
        return null;
    }

    @Override
    public OrderItemDto save(OrderItemDto orderItemDto) {
        return null;
    }

    @Override
    public Optional<OrderItemDto> partialUpdate(OrderItemDto orderItemDto) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
