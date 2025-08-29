package com.example.ecommerce.controller;

import com.example.ecommerce.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shop/orders")
public class UserOrderController {

  private final OrderRepository orderRepo;

  public UserOrderController(OrderRepository orderRepo) {
    this.orderRepo = orderRepo;
  }

  private Long uid(HttpSession session) {
    Object id = session.getAttribute("USER_ID");
    if (id instanceof Long l) return l;
    if (id instanceof Integer i) return i.longValue();
    return null;
  }

  @GetMapping
  public String myOrders(HttpSession session, Model model) {
    Long userId = uid(session);
    if (userId == null) return "redirect:/login?next=/shop/orders";
    model.addAttribute("orders", orderRepo.findByUser_IdOrderByCreatedAtDesc(userId));
    return "shop/order-list";
  }

  @GetMapping("/{id}")
  public String orderDetail(@PathVariable Long id, HttpSession session, Model model) {
    Long userId = uid(session);
    if (userId == null) return "redirect:/login?next=/shop/orders/" + id;

    var order = orderRepo.findById(id).orElseThrow();
    if (!order.getUser().getId().equals(userId)) return "redirect:/shop/orders"; 

    model.addAttribute("order", order);
    return "shop/order-detail";
  }
}
