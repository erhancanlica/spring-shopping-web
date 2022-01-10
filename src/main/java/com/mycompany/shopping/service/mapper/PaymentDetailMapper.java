package com.mycompany.shopping.service.mapper;

import com.mycompany.shopping.domain.PaymentDetail;
import com.mycompany.shopping.service.dto.PaymentDetailDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentDetailMapper extends EntityMapper<PaymentDetailDto, PaymentDetail> {

    PaymentDetailDto toDto(PaymentDetail paymentDetail);

    PaymentDetail toEntity(PaymentDetailDto paymentDetailDto);
}
