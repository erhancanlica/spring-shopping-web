package com.mycompany.shopping.service.mapper;

import com.mycompany.shopping.domain.Product;
import com.mycompany.shopping.service.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper extends EntityMapper<ProductDto, Product> {

    ProductDto toDto(Product product);

    Product toEntity(ProductDto productDto);

}
