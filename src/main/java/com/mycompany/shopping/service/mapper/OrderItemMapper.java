package com.mycompany.shopping.service.mapper;

import com.mycompany.shopping.domain.OrderItem;
import com.mycompany.shopping.service.dto.OrderItemDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper extends EntityMapper<OrderItemDto, OrderItem> {


    OrderItemDto toDto(OrderItem orderItem);

    OrderItem toEntity(OrderItemDto orderItemDto);
}
