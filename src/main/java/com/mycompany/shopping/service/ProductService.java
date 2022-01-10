package com.mycompany.shopping.service;

import com.mycompany.shopping.service.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<ProductDto> findOne(Long id);

    List<ProductDto> findAll();

    ProductDto save(ProductDto productDto);

    void delete(Long id);
}
