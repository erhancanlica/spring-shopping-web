package com.mycompany.shopping.repository;

import com.mycompany.shopping.domain.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
