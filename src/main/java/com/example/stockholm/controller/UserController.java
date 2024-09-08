package com.example.stockholm.controller;

import com.example.stockholm.model.User;
import com.example.stockholm.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin"; // Теперь возвращаем admin.html, а не users.html
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
        }
        return "redirect:/admin/users"; // После удаления перенаправляем на admin
    }
}
