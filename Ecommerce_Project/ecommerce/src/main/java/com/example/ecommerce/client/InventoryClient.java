package com.example.ecommerce.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
  @GetMapping("/api/inventory/availability")
  Integer getAvailable(@RequestParam("productId") Long productId);
}
