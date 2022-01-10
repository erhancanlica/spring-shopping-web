package com.mycompany.shopping.service.mapper;

import com.mycompany.shopping.service.dto.CartItemDto;
import com.mycompany.shopping.domain.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper extends EntityMapper<CartItemDto, CartItem> {

    CartItemDto toDto(CartItem cartItem);

    CartItem toEntity(CartItemDto cartItemDto);
}
