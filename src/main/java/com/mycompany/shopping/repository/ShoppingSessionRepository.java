package com.mycompany.shopping.repository;

import com.mycompany.shopping.domain.ShoppingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ShoppingSessionRepository extends JpaRepository<ShoppingSession, Long> {
}
