package com.example.ecommerce.controller;

import com.example.ecommerce.domain.Cart;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/shop/checkout")
public class CheckoutController {

  private final CartService cartService;
  private final OrderService orderService;

  public CheckoutController(CartService cartService, OrderService orderService) {
    this.cartService = cartService;
    this.orderService = orderService;
  }

  private Long uid(HttpSession session) {
    Object id = session.getAttribute("USER_ID");
    if (id instanceof Long l) return l;
    if (id instanceof Integer i) return i.longValue();
    return null;
  }

  @GetMapping
  public String checkout(HttpSession session, Model model) {
    Long userId = uid(session);
    if (userId == null) return "redirect:/login?next=/shop/checkout";

    Cart cart = cartService.getOrCreateCart(userId);
    if (cart.getItems().isEmpty()) return "redirect:/shop/cart";

    model.addAttribute("cart", cart);
    model.addAttribute("summary", cartService.summarize(cart));
    return "shop/checkout";
  }

  @PostMapping
  public String placeOrder(@RequestParam String shippingAddress,
                           HttpSession session,
                           RedirectAttributes ra) {
    Long userId = uid(session);
    if (userId == null) return "redirect:/login?next=/shop/checkout";

    var order = orderService.placeOrder(userId, shippingAddress);
    ra.addFlashAttribute("success", "Order #" + order.getId() + " placed successfully");
    return "redirect:/shop/orders/" + order.getId();
  }
}
