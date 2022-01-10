package com.mycompany.shopping.service.dto;

import com.mycompany.shopping.domain.Product;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class DiscountDto {

    private Long id;

    private String name;

    private String description;

    private Float discountPercent;

    private Set<Product> products = new HashSet<>();
}
