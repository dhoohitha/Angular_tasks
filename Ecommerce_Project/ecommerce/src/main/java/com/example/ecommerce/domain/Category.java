package com.example.ecommerce.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "categories")
public class Category {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false, unique = true, length = 100)
  private String name;

  @Column(length = 255)
  private String description;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  private Category parent; // optional

  // getters/setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public Category getParent() { return parent; }
  public void setParent(Category parent) { this.parent = parent; }
}
