package com.example.ecommerce.controller;

import com.example.ecommerce.domain.User;
import com.example.ecommerce.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping({"/", "/login"})
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("error", error != null);
        return "auth/login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {

        Optional<User> userOpt = userRepository.findByUsernameAndPassword(username, password);
        if (userOpt.isEmpty()) {
            model.addAttribute("error", true);
            return "auth/login";
        }

        User u = userOpt.get();

  
        session.setAttribute("USER_ID", u.getId());
        session.setAttribute("USERNAME", u.getUsername());
        session.setAttribute("USER_ROLE", u.getRole());

        if ("ADMIN".equalsIgnoreCase(u.getRole())) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/shop/home";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
