package com.example.ecommerce.repository;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Page<Product> findByActiveTrue(Pageable pageable);
  Page<Product> findByActiveTrueAndCategory(Category category, Pageable pageable);
  Page<Product> findByActiveTrueAndNameContainingIgnoreCase(String q, Pageable pageable);
}
