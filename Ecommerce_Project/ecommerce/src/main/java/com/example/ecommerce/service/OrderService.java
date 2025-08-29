package com.example.ecommerce.service;

import com.example.ecommerce.domain.*;
import com.example.ecommerce.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class OrderService {

  private final CartRepository cartRepo;
  private final ProductRepository productRepo;
  private final OrderRepository orderRepo;
  private final UserRepository userRepo;

  public OrderService(CartRepository cartRepo, ProductRepository productRepo,
                      OrderRepository orderRepo, UserRepository userRepo) {
    this.cartRepo = cartRepo;
    this.productRepo = productRepo;
    this.orderRepo = orderRepo;
    this.userRepo = userRepo;
  }

  @Transactional
  public Order placeOrder(Long userId, String shippingAddress) {
    var user = userRepo.findById(userId).orElseThrow();
    var cart = cartRepo.findByUser_Id(userId).orElseThrow(() -> new IllegalStateException("Cart is empty"));

    if (cart.getItems().isEmpty()) throw new IllegalStateException("Cart is empty");

    // Re-check stock and decrement
    for (var ci : cart.getItems()) {
      var p = productRepo.findById(ci.getProduct().getId()).orElseThrow();
      int newStock = p.getStock() - ci.getQuantity();
      if (newStock < 0) throw new IllegalArgumentException("Insufficient stock for " + p.getName());
      p.setStock(newStock);
      productRepo.save(p);
    }

    // Create order
    var order = new Order();
    order.setUser(user);
    order.setShippingAddress(shippingAddress);

    BigDecimal subtotal = BigDecimal.ZERO;

    for (var ci : cart.getItems()) {
      var oi = new OrderItem();
      oi.setOrder(order);
      oi.setProductId(ci.getProduct().getId());
      oi.setProductName(ci.getProduct().getName());
      oi.setUnitPrice(ci.getUnitPrice());
      oi.setQuantity(ci.getQuantity());
      oi.setLineTotal(ci.getUnitPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));
      order.getItems().add(oi);
      subtotal = subtotal.add(oi.getLineTotal());
    }

    order.setSubtotal(subtotal);
    order.setTotal(subtotal); // no shipping/tax yet
    order.setStatus(OrderStatus.NEW);

    var saved = orderRepo.save(order);

    // Clear cart
    cart.getItems().clear();
    cartRepo.save(cart);

    return saved;
  }

@Transactional
public Order updateStatus(Long orderId, OrderStatus to) {
 var order = orderRepo.findById(orderId).orElseThrow();

 var from = order.getStatus();

 boolean ok =
     (from == OrderStatus.NEW && (to == OrderStatus.PAID || to == OrderStatus.CANCELED)) ||
     (from == OrderStatus.PAID && to == OrderStatus.SHIPPED);

 if (!ok) throw new IllegalArgumentException("Invalid status transition: " + from + " -> " + to);

 order.setStatus(to);
 return orderRepo.save(order);
}

}
