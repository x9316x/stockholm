package com.example.stockholm.controller;

import com.example.stockholm.model.Section;
import com.example.stockholm.model.StudentProgress;
import com.example.stockholm.model.User;
import com.example.stockholm.repository.SectionRepository;
import com.example.stockholm.repository.StudentProgressRepository;
import com.example.stockholm.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class StudentController {

    private final StudentProgressRepository studentProgressRepository;
    private final UserRepository userRepository;
    private final SectionRepository sectionRepository;

    public StudentController(StudentProgressRepository studentProgressRepository, UserRepository userRepository, SectionRepository sectionRepository) {
        this.studentProgressRepository = studentProgressRepository;
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
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

            // Получаем список всех разделов
            model.addAttribute("sections", sectionRepository.findAll());
        }

        return "student"; // Возвращаем имя шаблона student.html
    }

    @PostMapping("/student/complete-section")
    public String toggleSectionCompletion(@RequestParam Long sectionId, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            StudentProgress progress = studentProgressRepository.findByUser(user).orElse(new StudentProgress(user, 0, 0));
            Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new IllegalArgumentException("Invalid section Id:" + sectionId));

            if (progress.getCompletedSections().contains(section)) {
                // Если раздел уже пройден, удаляем его из списка пройденных
                progress.removeCompletedSection(section);
            } else {
                // Если раздел не пройден, добавляем его
                progress.addCompletedSection(section);
            }

            studentProgressRepository.save(progress); // Сохраняем изменения в прогрессе студента
        }

        return "redirect:/student"; // Перенаправляем обратно на страницу студента
    }
}
