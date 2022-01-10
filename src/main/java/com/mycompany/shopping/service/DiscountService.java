package com.mycompany.shopping.service;

import com.mycompany.shopping.service.dto.DiscountDto;

import java.util.List;
import java.util.Optional;

public interface DiscountService {


    Optional<DiscountDto> findOne(Long id);

    List<DiscountDto> findAll();

    DiscountDto save(DiscountDto discountDto);

    void delete(Long id);
}
