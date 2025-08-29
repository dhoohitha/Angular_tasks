package com.example.ecommerce.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
public class CartItem {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "cart_id")
  private Cart cart;

  @ManyToOne(optional = false)
  @JoinColumn(name = "product_id")
  private Product product;

  @Column(nullable = false)
  private Integer quantity;

  @Column(name="unit_price", precision = 10, scale = 2, nullable = false)
  private BigDecimal unitPrice; 

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public Cart getCart() { return cart; }
  public void setCart(Cart cart) { this.cart = cart; }

  public Product getProduct() { return product; }
  public void setProduct(Product product) { this.product = product; }

  public Integer getQuantity() { return quantity; }
  public void setQuantity(Integer quantity) { this.quantity = quantity; }

  public BigDecimal getUnitPrice() { return unitPrice; }
  public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

  public BigDecimal getLineTotal() {
    return unitPrice.multiply(BigDecimal.valueOf(quantity));
  }
}
