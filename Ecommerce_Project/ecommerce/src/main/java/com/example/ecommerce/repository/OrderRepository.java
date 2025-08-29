package com.example.ecommerce.repository;

import com.example.ecommerce.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findByUser_IdOrderByCreatedAtDesc(Long userId);
}
