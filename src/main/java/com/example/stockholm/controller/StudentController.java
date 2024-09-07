package com.example.stockholm.controller;

import com.example.stockholm.model.StudentProgress;
import com.example.stockholm.model.User;
import com.example.stockholm.repository.StudentProgressRepository;
import com.example.stockholm.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class StudentController {

    private final StudentProgressRepository studentProgressRepository;
    private final UserRepository userRepository;

    public StudentController(StudentProgressRepository studentProgressRepository, UserRepository userRepository) {
        this.studentProgressRepository = studentProgressRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/student")
    public String studentPage(Model model, Authentication authentication) {
        // Получаем текущего пользователя
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Получаем прогресс студента по его ID
            Optional<StudentProgress> progressOptional = studentProgressRepository.findByUser(user);

            StudentProgress progress = progressOptional.orElse(new StudentProgress(user, 0, 0));
            model.addAttribute("progress", progress);
        }

        return "student"; // Возвращаем имя шаблона student.html
    }
}
