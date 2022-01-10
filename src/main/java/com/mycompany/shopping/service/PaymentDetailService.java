package com.mycompany.shopping.service;

import com.mycompany.shopping.service.dto.PaymentDetailDto;

import java.util.List;
import java.util.Optional;

public interface PaymentDetailService {

    Optional<PaymentDetailDto> findOne(Long id);

    List<PaymentDetailDto> findAll();

    PaymentDetailDto save(PaymentDetailDto paymentDetailDto);

    Optional<PaymentDetailDto> partialUpdate(PaymentDetailDto paymentDetailDto);

    void delete(Long id);
}
