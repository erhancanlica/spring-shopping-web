package com.mycompany.shopping.service.mapper;

import com.mycompany.shopping.service.dto.OrderDetailDto;
import com.mycompany.shopping.domain.OrderDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper extends EntityMapper<OrderDetailDto, OrderDetail> {

    OrderDetailDto toDto(OrderDetail orderDetail);

    OrderDetail toEntity(OrderDetailDto orderDetailDto);
}
