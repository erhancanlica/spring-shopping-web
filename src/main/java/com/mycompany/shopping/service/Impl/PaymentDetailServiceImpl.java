package com.mycompany.shopping.service.Impl;

import com.mycompany.shopping.service.PaymentDetailService;
import com.mycompany.shopping.service.dto.PaymentDetailDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentDetailServiceImpl implements PaymentDetailService {
    @Override
    public Optional<PaymentDetailDto> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public List<PaymentDetailDto> findAll() {
        return null;
    }

    @Override
    public PaymentDetailDto save(PaymentDetailDto paymentDetailDto) {
        return null;
    }

    @Override
    public Optional<PaymentDetailDto> partialUpdate(PaymentDetailDto paymentDetailDto) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
