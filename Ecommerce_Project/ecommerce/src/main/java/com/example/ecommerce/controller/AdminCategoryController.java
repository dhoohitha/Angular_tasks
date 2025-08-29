package com.example.ecommerce.controller;

import com.example.ecommerce.domain.Category;
import com.example.ecommerce.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

  private final CategoryRepository categoryRepo;

  public AdminCategoryController(CategoryRepository categoryRepo) {
    this.categoryRepo = categoryRepo;
  }

  @GetMapping
  public String list(Model model) {
    model.addAttribute("categories", categoryRepo.findAll(Sort.by("name")));
    return "admin/category-list";
  }

  @GetMapping("/create")
  public String createForm(Model model) {
    model.addAttribute("category", new Category());
    model.addAttribute("allCategories", categoryRepo.findAll(Sort.by("name"))); // for parent
    return "admin/category-form";
  }

  @PostMapping("/create")
  public String create(@Valid @ModelAttribute("category") Category category, BindingResult br, Model model) {
    if (br.hasErrors()) {
      model.addAttribute("allCategories", categoryRepo.findAll(Sort.by("name")));
      return "admin/category-form";
    }
    categoryRepo.save(category);
    return "redirect:/admin/categories";
  }

  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable Long id, Model model) {
    Category c = categoryRepo.findById(id).orElseThrow();
    model.addAttribute("category", c);
    model.addAttribute("allCategories", categoryRepo.findAll(Sort.by("name")));
    return "admin/category-form";
  }

  @PostMapping("/{id}/edit")
  public String edit(@PathVariable Long id, @Valid @ModelAttribute("category") Category category, BindingResult br, Model model) {
    if (br.hasErrors()) {
      model.addAttribute("allCategories", categoryRepo.findAll(Sort.by("name")));
      return "admin/category-form";
    }
    category.setId(id);
    categoryRepo.save(category);
    return "redirect:/admin/categories";
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id) {
    categoryRepo.deleteById(id);
    return "redirect:/admin/categories";
  }
}
