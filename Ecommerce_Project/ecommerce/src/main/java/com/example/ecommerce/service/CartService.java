package com.example.ecommerce.service;

import com.example.ecommerce.domain.*;
import com.example.ecommerce.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CartService {

  private final CartRepository cartRepo;
  private final CartItemRepository itemRepo;
  private final ProductRepository productRepo;
  private final UserRepository userRepo;

  public CartService(CartRepository cartRepo, CartItemRepository itemRepo,
                     ProductRepository productRepo, UserRepository userRepo) {
    this.cartRepo = cartRepo;
    this.itemRepo = itemRepo;
    this.productRepo = productRepo;
    this.userRepo = userRepo;
  }

  @Transactional
  public Cart getOrCreateCart(Long userId) {
    return cartRepo.findByUser_Id(userId).orElseGet(() -> {
      User u = userRepo.findById(userId).orElseThrow();
      Cart c = new Cart();
      c.setUser(u);
      return cartRepo.save(c);
    });
  }

  @Transactional
  public Cart addItem(Long userId, Long productId, int qty) {
    if (qty <= 0) qty = 1;
    Cart cart = getOrCreateCart(userId);
    Product p = productRepo.findById(productId).orElseThrow();

    if (!Boolean.TRUE.equals(p.getActive())) {
      throw new IllegalArgumentException("Product is inactive");
    }
    if (p.getStock() < qty) {
      throw new IllegalArgumentException("Insufficient stock");
    }

 
    CartItem existing = cart.getItems().stream()
        .filter(ci -> ci.getProduct().getId().equals(productId))
        .findFirst().orElse(null);

    if (existing != null) {
      int newQty = existing.getQuantity() + qty;
      if (newQty > p.getStock()) throw new IllegalArgumentException("Insufficient stock");
      existing.setQuantity(newQty);
    } else {
      CartItem ci = new CartItem();
      ci.setCart(cart);
      ci.setProduct(p);
      ci.setQuantity(qty);
      ci.setUnitPrice(p.getPrice() != null ? p.getPrice() : BigDecimal.ZERO);
      cart.getItems().add(ci);
    }
    return cartRepo.save(cart);
  }

  @Transactional
  public Cart updateItem(Long userId, Long itemId, int qty) {
    if (qty < 1) throw new IllegalArgumentException("Quantity must be >= 1");
    Cart cart = getOrCreateCart(userId);
    CartItem item = itemRepo.findById(itemId).orElseThrow();
    if (!item.getCart().getId().equals(cart.getId())) {
      throw new IllegalArgumentException("Item not in your cart");
    }
    Product p = item.getProduct();
    if (qty > p.getStock()) throw new IllegalArgumentException("Insufficient stock");
    item.setQuantity(qty);
    itemRepo.save(item);
    return cart;
  }

  @Transactional
  public Cart removeItem(Long userId, Long itemId) {
    Cart cart = getOrCreateCart(userId);
    CartItem item = itemRepo.findById(itemId).orElseThrow();
    if (!item.getCart().getId().equals(cart.getId())) {
      throw new IllegalArgumentException("Item not in your cart");
    }
    cart.getItems().remove(item);
    itemRepo.delete(item);
    return cart;
  }

  @Transactional
  public void clear(Long userId) {
    Cart cart = getOrCreateCart(userId);
    cart.getItems().clear();
    cartRepo.save(cart);
  }

  public CartSummary summarize(Cart cart) {
    var subtotal = cart.getItems().stream()
        .map(CartItem::getLineTotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    return new CartSummary(subtotal, subtotal);
  }

  public record CartSummary(BigDecimal subtotal, BigDecimal total) {}
}
