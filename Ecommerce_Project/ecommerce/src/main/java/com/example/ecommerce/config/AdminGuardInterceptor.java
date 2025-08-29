package com.example.ecommerce.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminGuardInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String uri = request.getRequestURI();
    // only guard /admin/**
    if (!uri.startsWith("/admin")) return true;

    HttpSession session = request.getSession(false);
    String role = (session != null) ? (String) session.getAttribute("USER_ROLE") : null;

    if (role == null) {
      response.sendRedirect(request.getContextPath() + "/login?next=" + uri);
      return false;
    }
    if (!"ADMIN".equalsIgnoreCase(role)) {
      // non-admins get bounced to shop home
      response.sendRedirect(request.getContextPath() + "/shop/home");
      return false;
    }
    return true;
  }
}
