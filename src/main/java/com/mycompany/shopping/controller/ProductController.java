package com.mycompany.shopping.controller;

import com.mycompany.shopping.service.dto.ProductDto;
import com.mycompany.shopping.repository.ProductRepository;
import com.mycompany.shopping.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    private final ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Optional<ProductDto>> getProduct(@PathVariable Long id) {
        log.debug("REST request to Product : {}", id);
        Optional<ProductDto> productDto = productService.findOne(id);
        return ResponseEntity.ok().body(productDto);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        log.debug("REST request to all Products");
        List<ProductDto> productDtoList = productService.findAll();
        return ResponseEntity.ok().body(productDtoList);
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) throws Exception {
        log.debug("REST request to create Product");

        if (productDto.getId() != null) {
            throw new Exception("A new product cannot already have an ID");
        }

        ProductDto product = productService.save(productDto);
        return ResponseEntity
                .created(new URI("/api/product/" + product.getId()))
                .body(product);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable(value = "id", required = false) final Long id,
                                                    @RequestBody ProductDto productDto) throws Exception {
        log.debug("REST request to update Product : {}", id);

        if (productDto.getId() != null) {
            throw new Exception("A new product cannot already have an ID");
        }
        if (!Objects.equals(id, productDto.getId())) {
            throw new Exception("Invalid id");
        }
        if (!productRepository.existsById(productDto.getId())) {
            throw new Exception("Entity Not Found");
        }

        ProductDto product = productService.save(productDto);
        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
