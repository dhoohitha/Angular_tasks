package com.example.ecommerce.controller;

import com.example.ecommerce.domain.Category;
import com.example.ecommerce.domain.Product;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shop")
public class CatalogController {

  private final CategoryRepository categoryRepo;
  private final ProductRepository productRepo;

  public CatalogController(CategoryRepository categoryRepo, ProductRepository productRepo) {
    this.categoryRepo = categoryRepo;
    this.productRepo = productRepo;
  }

  @GetMapping("/home")
  public String home(Model model) {
    model.addAttribute("categories", categoryRepo.findAll());
    return "shop/home";
  }

  @GetMapping("/category/{id}")
  public String productsByCategory(@PathVariable Long id,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "12") int size,
                                   Model model) {
    Category c = categoryRepo.findById(id).orElseThrow();
    Page<Product> products = productRepo.findByActiveTrueAndCategory(c, PageRequest.of(page, size));
    model.addAttribute("category", c);
    model.addAttribute("products", products);
    return "shop/products";
  }

  @GetMapping("/search")
  public String search(@RequestParam String q,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "12") int size,
                       Model model) {
    Page<Product> products = productRepo.findByActiveTrueAndNameContainingIgnoreCase(q, PageRequest.of(page, size));
    model.addAttribute("q", q);
    model.addAttribute("products", products);
    return "shop/products";
  }

  @GetMapping("/product/{id}")
  public String productDetail(@PathVariable Long id, Model model) {
    Product p = productRepo.findById(id).orElseThrow();
    if (!Boolean.TRUE.equals(p.getActive())) return "redirect:/shop/home";
    model.addAttribute("p", p);
    return "shop/product-detail";
  }
}
