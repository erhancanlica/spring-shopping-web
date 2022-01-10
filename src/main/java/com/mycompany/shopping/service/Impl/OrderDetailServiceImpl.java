package com.mycompany.shopping.service.Impl;

import com.mycompany.shopping.service.OrderDetailService;
import com.mycompany.shopping.service.dto.OrderDetailDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Override
    public Optional<OrderDetailDto> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public List<OrderDetailDto> findAll() {
        return null;
    }

    @Override
    public OrderDetailDto save(OrderDetailDto orderDetailDto) {
        return null;
    }

    @Override
    public Optional<OrderDetailDto> partialUpdate(OrderDetailDto orderDetailDto) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
