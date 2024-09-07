package com.example.stockholm.controller;

import com.example.stockholm.model.Section;
import com.example.stockholm.model.StudentCompletedSection;
import com.example.stockholm.model.StudentProgress;
import com.example.stockholm.model.User;
import com.example.stockholm.repository.SectionRepository;
import com.example.stockholm.repository.StudentCompletedSectionRepository;
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
    private final StudentCompletedSectionRepository studentCompletedSectionRepository;

    public StudentController(StudentProgressRepository studentProgressRepository, UserRepository userRepository, SectionRepository sectionRepository, StudentCompletedSectionRepository studentCompletedSectionRepository) {
        this.studentProgressRepository = studentProgressRepository;
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.studentCompletedSectionRepository = studentCompletedSectionRepository;
    }

    @GetMapping("/student")
    public String studentPage(Model model, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<StudentProgress> progressOptional = studentProgressRepository.findByUser(user);

            StudentProgress progress = progressOptional.orElse(new StudentProgress(user, 0, 0));
            model.addAttribute("progress", progress);

            model.addAttribute("sections", sectionRepository.findAll());
        }

        return "student";
    }

    @PostMapping("/student/complete-section")
    public String toggleSectionCompletion(@RequestParam Long sectionId, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            StudentProgress progress = studentProgressRepository.findByUser(user).orElse(new StudentProgress(user, 0, 0));

            // Сначала сохраняем StudentProgress, если он еще не сохранен
            if (progress.getId() == null) {
                studentProgressRepository.save(progress);
            }

            Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new IllegalArgumentException("Invalid section Id:" + sectionId));

            Optional<StudentCompletedSection> completedSectionOptional = studentCompletedSectionRepository.findByStudentAndSection(user, section);
            if (completedSectionOptional.isPresent()) {
                studentCompletedSectionRepository.delete(completedSectionOptional.get());
            } else {
                StudentCompletedSection newCompletedSection = new StudentCompletedSection(user, section, progress, true);
                studentCompletedSectionRepository.save(newCompletedSection);
                progress.addCompletedSection(newCompletedSection);
            }

            studentProgressRepository.save(progress);
        }

        return "redirect:/student";
    }
}
