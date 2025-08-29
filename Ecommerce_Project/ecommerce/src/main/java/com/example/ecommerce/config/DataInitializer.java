package com.example.ecommerce.config;

import com.example.ecommerce.domain.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
  private final UserRepository userRepo;

  public DataInitializer(UserRepository userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  public void run(String... args) {
    // admin / admin123
    userRepo.findByUsernameAndPassword("admin", "admin123")
        .orElseGet(() -> {
          User u = new User();
          u.setUsername("admin");
          u.setPassword("admin123");
          u.setRole("ADMIN");
          u.setEnabled(true);
          return userRepo.save(u);
        });

    // user / user123
    userRepo.findByUsernameAndPassword("user", "user123")
        .orElseGet(() -> {
          User u = new User();
          u.setUsername("user");
          u.setPassword("user123");
          u.setRole("USER");
          u.setEnabled(true);
          return userRepo.save(u);
        });
  }
}
