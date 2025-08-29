
package com.example.ecommerce.api;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Products")
@RestController
@RequestMapping("/api/products")
public class ProductApi {
  private final ProductRepository repo;
  public ProductApi(ProductRepository repo) { this.repo = repo; }

  @Operation(summary = "List all products")
  @GetMapping
  public List<Product> list() { return repo.findAll(); }

  @Operation(summary = "Get one product by id")
  @GetMapping("/{id}")
  public Product get(@PathVariable Long id) {
    return repo.findById(id).orElseThrow();
  }
}
