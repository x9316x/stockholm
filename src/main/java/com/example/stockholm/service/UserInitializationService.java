package com.example.stockholm.service;

import com.example.stockholm.model.User;
import com.example.stockholm.model.Role;
import com.example.stockholm.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class UserInitializationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInitializationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        // Создаём админа только один раз
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }

        // Для простоты создаём двух студентов
        if (userRepository.findByUsername("student").isEmpty()) {
            User student1 = new User();
            student1.setUsername("student");
            student1.setPassword(passwordEncoder.encode("student123"));
            student1.setRole(Role.STUDENT);
            userRepository.save(student1);
        }

        if (userRepository.findByUsername("student2").isEmpty()) {
            User student2 = new User();
            student2.setUsername("student2");
            student2.setPassword(passwordEncoder.encode("student234"));
            student2.setRole(Role.STUDENT);
            userRepository.save(student2);
        }
    }
}
