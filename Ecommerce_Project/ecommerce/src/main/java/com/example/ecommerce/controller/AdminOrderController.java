package com.example.ecommerce.controller;

import com.example.ecommerce.domain.OrderStatus;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

  private final OrderRepository orderRepo;
  private final OrderService orderService;

  public AdminOrderController(OrderRepository orderRepo, OrderService orderService) {
    this.orderRepo = orderRepo;
    this.orderService = orderService;
  }

  @GetMapping
  public String list(@RequestParam(required = false) OrderStatus status, Model model) {
    var orders = (status == null) ? orderRepo.findAll()
        : orderRepo.findAll().stream().filter(o -> o.getStatus() == status).toList();
    model.addAttribute("orders", orders);
    model.addAttribute("status", status);
    model.addAttribute("allStatuses", OrderStatus.values());
    return "admin/order-list";
  }

  @GetMapping("/{id}")
  public String detail(@PathVariable Long id, Model model) {
    var order = orderRepo.findById(id).orElseThrow();
    model.addAttribute("order", order);
    model.addAttribute("allStatuses", OrderStatus.values());
    return "admin/order-detail";
  }

  @PostMapping("/{id}/status")
  public String changeStatus(@PathVariable Long id,
                             @RequestParam OrderStatus to,
                             RedirectAttributes ra) {
    try {
      orderService.updateStatus(id, to);
      ra.addFlashAttribute("success", "Status updated to " + to);
    } catch (IllegalArgumentException ex) {
      ra.addFlashAttribute("error", ex.getMessage());
    }
    return "redirect:/admin/orders/" + id;
  }
}
