package com.mycompany.shopping.repository;

import com.mycompany.shopping.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
