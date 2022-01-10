package com.mycompany.shopping.service.mapper;

import com.mycompany.shopping.domain.ShoppingSession;
import com.mycompany.shopping.service.dto.ShoppingSessionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShoppingSessionMapper extends EntityMapper<ShoppingSessionDto, ShoppingSession> {


    ShoppingSessionDto toDto(ShoppingSession shoppingSession);

    ShoppingSession toEntity(ShoppingSessionDto shoppingSessionDto);
}
