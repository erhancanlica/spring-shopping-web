package com.mycompany.shopping.service.Impl;

import com.mycompany.shopping.domain.Product;
import com.mycompany.shopping.service.dto.ProductDto;
import com.mycompany.shopping.service.mapper.ProductMapper;
import com.mycompany.shopping.repository.ProductRepository;
import com.mycompany.shopping.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDto> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return  productRepository.findById(id).map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        log.debug("Request to get all Products");
        return productRepository.findAll().stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        log.debug("Request to save Product : {}", productDto);
        Product product = productMapper.toEntity(productDto);
        product =  productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
    }
}
