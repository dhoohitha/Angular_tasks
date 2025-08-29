package com.example.ecommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Bean
  OpenAPI ecommerceOpenAPI() {
    return new OpenAPI()
        .info(new Info()
          .title("Ecommerce API")
          .description("Catalog, Cart, Checkout, and Admin endpoints")
          .version("v1"));
  }
}
