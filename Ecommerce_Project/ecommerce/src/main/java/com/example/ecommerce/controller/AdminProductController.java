package com.example.ecommerce.controller;

import com.example.ecommerce.domain.Category;
import com.example.ecommerce.domain.Product;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

  private final ProductRepository productRepo;
  private final CategoryRepository categoryRepo;

  public AdminProductController(ProductRepository productRepo, CategoryRepository categoryRepo) {
    this.productRepo = productRepo;
    this.categoryRepo = categoryRepo;
  }

  @GetMapping
  public String list(@RequestParam(defaultValue = "0") int page,
                     @RequestParam(defaultValue = "10") int size,
                     Model model) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Product> products = productRepo.findAll(pageable);
    model.addAttribute("products", products);
    return "admin/product-list";
  }

  @GetMapping("/create")
  public String createForm(Model model) {
    model.addAttribute("product", new Product());
    model.addAttribute("categories", categoryRepo.findAll());
    return "admin/product-form";
  }

  @PostMapping("/create")
  public String create(@Valid @ModelAttribute("product") Product product, BindingResult br, Model model,
                       @RequestParam Long categoryId) {
    if (br.hasErrors()) {
      model.addAttribute("categories", categoryRepo.findAll());
      return "admin/product-form";
    }
    Category cat = categoryRepo.findById(categoryId).orElseThrow();
    product.setCategory(cat);
    productRepo.save(product);
    return "redirect:/admin/products";
  }

  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable Long id, Model model) {
    Product p = productRepo.findById(id).orElseThrow();
    model.addAttribute("product", p);
    model.addAttribute("categories", categoryRepo.findAll());
    model.addAttribute("selectedCategoryId", p.getCategory().getId());
    return "admin/product-form";
  }

  @PostMapping("/{id}/edit")
  public String edit(@PathVariable Long id, @Valid @ModelAttribute("product") Product product, BindingResult br,
                     Model model, @RequestParam Long categoryId) {
    if (br.hasErrors()) {
      model.addAttribute("categories", categoryRepo.findAll());
      model.addAttribute("selectedCategoryId", categoryId);
      return "admin/product-form";
    }
    Category cat = categoryRepo.findById(categoryId).orElseThrow();
    product.setId(id);
    product.setCategory(cat);
    productRepo.save(product);
    return "redirect:/admin/products";
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id) {
    productRepo.deleteById(id);
    return "redirect:/admin/products";
  }
}
