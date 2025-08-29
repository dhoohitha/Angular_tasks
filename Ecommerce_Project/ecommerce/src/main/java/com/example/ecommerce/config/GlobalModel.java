package com.example.ecommerce.config;

import com.example.ecommerce.repository.CartRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
@ControllerAdvice
public class GlobalModel {
  private final CartRepository cartRepo;
  public GlobalModel(CartRepository cartRepo) { this.cartRepo = cartRepo; }

  @ModelAttribute("cartCount")
  public Integer cartCount(HttpSession session) {
    Object id = session.getAttribute("USER_ID");
    Long uid = (id instanceof Long l) ? l : (id instanceof Integer i ? i.longValue() : null);
    if (uid == null) return 0;
    return cartRepo.findByUser_Id(uid).map(c -> c.getItems().size()).orElse(0);
  }
}
