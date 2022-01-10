package com.mycompany.shopping.service;

import com.mycompany.shopping.service.dto.OrderDetailDto;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {

    Optional<OrderDetailDto> findOne(Long id);

    List<OrderDetailDto> findAll();

    OrderDetailDto save(OrderDetailDto orderDetailDto);

    Optional<OrderDetailDto> partialUpdate(OrderDetailDto orderDetailDto);

    void delete(Long id);
}
