package com.example.stockholm.service;

import com.example.stockholm.model.User;
import com.example.stockholm.model.Role;
import com.example.stockholm.model.StudentProgress;
import com.example.stockholm.repository.UserRepository;
import com.example.stockholm.repository.StudentProgressRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class UserInitializationService {

    private final UserRepository userRepository;
    private final StudentProgressRepository progressRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInitializationService(UserRepository userRepository,
                                     StudentProgressRepository progressRepository,
                                     PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.progressRepository = progressRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        // Создаём админа
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }

        // Динамическое создание студентов и их прогресса
        List<String> studentNames = List.of("student", "student2");  // Можно расширить список
        List<Integer> lessonsCompleted = List.of(2, 3);  // Пример прогресса для студентов
        List<Integer> tasksCompleted = List.of(5, 7);    // Пример заданий для студентов

        for (int i = 0; i < studentNames.size(); i++) {
            String studentName = studentNames.get(i);

            Optional<User> studentOptional = userRepository.findByUsername(studentName);

            User student;
            if (studentOptional.isEmpty()) {
                // Создаём нового студента
                student = new User();
                student.setUsername(studentName);
                student.setPassword(passwordEncoder.encode(studentName + "pass"));  // Пример пароля
                student.setRole(Role.STUDENT);
                userRepository.save(student);
            } else {
                student = studentOptional.get();
            }

            // Создаём запись о прогрессе, если её нет
            if (progressRepository.findByUser(student).isEmpty()) {
                StudentProgress progress = new StudentProgress(student, lessonsCompleted.get(i), tasksCompleted.get(i));
                progressRepository.save(progress);
            }
        }
    }
}
