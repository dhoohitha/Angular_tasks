package com.example.ecommerce.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false, length = 120)
  private String name;

  @Column(length = 2000)
  private String description;

  @NotNull
  @DecimalMin(value = "0.0", inclusive = true)
  @Column(precision = 10, scale = 2, nullable = false)
  private BigDecimal price;

  @NotNull
  @Min(0)
  @Column(nullable = false)
  private Integer stock;

  @Column(length = 64, unique = true)
  private String sku;

  @Column(length = 500)
  private String imageUrl;

  @Column(nullable = false)
  private Boolean active = true;

  @ManyToOne(optional = false)
  @JoinColumn(name = "category_id")
  private Category category;

  // getters/setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public BigDecimal getPrice() { return price; }
  public void setPrice(BigDecimal price) { this.price = price; }
  public Integer getStock() { return stock; }
  public void setStock(Integer stock) { this.stock = stock; }
  public String getSku() { return sku; }
  public void setSku(String sku) { this.sku = sku; }
  public String getImageUrl() { return imageUrl; }
  public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
  public Boolean getActive() { return active; }
  public void setActive(Boolean active) { this.active = active; }
  public Category getCategory() { return category; }
  public void setCategory(Category category) { this.category = category; }
}
