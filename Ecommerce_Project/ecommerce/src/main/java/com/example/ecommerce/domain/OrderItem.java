package com.example.ecommerce.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity @Table(name = "order_items")
public class OrderItem {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false) @JoinColumn(name = "order_id")
  private Order order;

  // snapshot product info
  @Column(nullable = false)
  private Long productId;

  @Column(nullable = false, length = 160)
  private String productName;

  @Column(precision = 10, scale = 2, nullable = false)
  private BigDecimal unitPrice;

  @Column(nullable = false)
  private Integer quantity;

  @Column(precision = 10, scale = 2, nullable = false)
  private BigDecimal lineTotal;

  // getters/setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Order getOrder() { return order; }
  public void setOrder(Order order) { this.order = order; }
  public Long getProductId() { return productId; }
  public void setProductId(Long productId) { this.productId = productId; }
  public String getProductName() { return productName; }
  public void setProductName(String productName) { this.productName = productName; }
  public BigDecimal getUnitPrice() { return unitPrice; }
  public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
  public Integer getQuantity() { return quantity; }
  public void setQuantity(Integer quantity) { this.quantity = quantity; }
  public BigDecimal getLineTotal() { return lineTotal; }
  public void setLineTotal(BigDecimal lineTotal) { this.lineTotal = lineTotal; }
}
