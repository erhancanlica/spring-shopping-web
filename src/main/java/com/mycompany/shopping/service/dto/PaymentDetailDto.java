package com.mycompany.shopping.service.dto;

import com.mycompany.shopping.domain.OrderDetail;
import lombok.Data;

@Data
public class PaymentDetailDto {

    private Long id;

    private OrderDetail orderDetail;

    private Long amount;

    private String provider;

    private String status;
}
