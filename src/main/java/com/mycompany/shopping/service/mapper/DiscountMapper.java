package com.mycompany.shopping.service.mapper;

import com.mycompany.shopping.service.dto.DiscountDto;
import com.mycompany.shopping.domain.Discount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface DiscountMapper extends EntityMapper<DiscountDto, Discount> {

    DiscountDto toDto(Discount discount);

    Discount toEntity(DiscountDto discountDto);
}
