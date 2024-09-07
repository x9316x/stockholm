package com.example.stockholm.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    // Маршрут для отображения страницы логина
    @GetMapping("/login")
    public ModelAndView showLoginPage(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Проверяем роли пользователя и перенаправляем в зависимости от роли
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return new ModelAndView("redirect:/admin");
            } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"))) {
                return new ModelAndView("redirect:/student");
            }
        }

        // Если пользователь не авторизован, отображаем страницу логина
        return new ModelAndView("login");
    }
}
