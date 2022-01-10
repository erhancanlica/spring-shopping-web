package com.mycompany.shopping.repository;

import com.mycompany.shopping.domain.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long> {
}
