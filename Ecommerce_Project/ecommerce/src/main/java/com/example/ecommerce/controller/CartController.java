package com.example.ecommerce.controller;

import com.example.ecommerce.domain.Cart;
import com.example.ecommerce.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/shop/cart")
public class CartController {

  private final CartService cartService;

  public CartController(CartService cartService) {
    this.cartService = cartService;
  }


  private Long currentUserId(HttpSession session) {
    Object id = session.getAttribute("USER_ID");
    if (id instanceof Long l) return l;
    if (id instanceof Integer i) return i.longValue();
    return null;
  }

  @GetMapping
  public String viewCart(HttpSession session, Model model) {
    Long uid = currentUserId(session);
    if (uid == null) return "redirect:/login?next=/shop/cart";

    Cart cart = cartService.getOrCreateCart(uid);
    var summary = cartService.summarize(cart);
    model.addAttribute("cart", cart);
    model.addAttribute("summary", summary);
    return "shop/cart";
  }

  @PostMapping("/add/{productId}")
  public String addToCart(@PathVariable Long productId,
                          @RequestParam(defaultValue = "1") int quantity,
                          HttpSession session,
                          RedirectAttributes ra) {
    Long uid = currentUserId(session);
    if (uid == null) return "redirect:/login?next=/shop/product/" + productId;

    try {
      cartService.addItem(uid, productId, quantity);
      ra.addFlashAttribute("success", "Item added to cart");
      return "redirect:/shop/cart";
    } catch (IllegalArgumentException ex) {
      ra.addFlashAttribute("error", ex.getMessage());
      return "redirect:/shop/product/" + productId;
    }
  }

  @PostMapping("/update/{itemId}")
  public String updateItem(@PathVariable Long itemId,
                           @RequestParam int quantity,
                           HttpSession session,
                           RedirectAttributes ra) {
    Long uid = currentUserId(session);
    if (uid == null) return "redirect:/login?next=/shop/cart";
    try {
      cartService.updateItem(uid, itemId, quantity);
      ra.addFlashAttribute("success", "Quantity updated");
    } catch (IllegalArgumentException ex) {
      ra.addFlashAttribute("error", ex.getMessage());
    }
    return "redirect:/shop/cart";
  }

  @PostMapping("/remove/{itemId}")
  public String removeItem(@PathVariable Long itemId,
                           HttpSession session,
                           RedirectAttributes ra) {
    Long uid = currentUserId(session);
    if (uid == null) return "redirect:/login?next=/shop/cart";
    cartService.removeItem(uid, itemId);
    ra.addFlashAttribute("info", "Item removed from cart");
    return "redirect:/shop/cart";
  }

  @PostMapping("/clear")
  public String clearCart(HttpSession session, RedirectAttributes ra) {
    Long uid = currentUserId(session);
    if (uid == null) return "redirect:/login?next=/shop/cart";
    cartService.clear(uid);
    ra.addFlashAttribute("warning", "Cart cleared");
    return "redirect:/shop/cart";
  }
}
