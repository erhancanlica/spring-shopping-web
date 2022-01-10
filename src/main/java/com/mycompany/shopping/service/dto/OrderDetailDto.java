package com.mycompany.shopping.service.dto;

import com.mycompany.shopping.domain.OrderItem;
import com.mycompany.shopping.domain.PaymentDetail;
import com.mycompany.shopping.domain.User;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class OrderDetailDto {

    private Long id;

    private User user;

    private PaymentDetail paymentDetail;

    private Float total;

    private Set<OrderItem> orderItems = new HashSet<>();
}
