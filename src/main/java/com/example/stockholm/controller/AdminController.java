package com.example.stockholm.controller;

import com.example.stockholm.model.User;
import com.example.stockholm.model.Role;
import com.example.stockholm.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin";  // Страница с формой для добавления пользователей
    }

    @PostMapping("/admin/addUser")
    public String addUser(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam Role role) {
        if (userRepository.findByUsername(username).isEmpty()) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(passwordEncoder.encode(password));
            newUser.setRole(role);
            userRepository.save(newUser);
        }
        // Перенаправление на страницу /admin после добавления пользователя
        return "redirect:/admin";
    }
}
