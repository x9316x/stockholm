package com.example.stockholm.controller;

import com.example.stockholm.model.Section;
import com.example.stockholm.repository.SectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/sections")
public class SectionController {

    private final SectionRepository sectionRepository;

    public SectionController(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @GetMapping
    public String listSections(Model model) {
        model.addAttribute("sections", sectionRepository.findAll());
        return "section_list"; // шаблон для отображения списка разделов
    }

    @GetMapping("/add")
    public String showAddSectionForm(Model model) {
        model.addAttribute("section", new Section());
        return "add_section"; // шаблон для добавления раздела
    }

    // Явно указываем параметры формы
    @PostMapping("/add")
    public String addSection(@RequestParam String title, @RequestParam String description) {
        Section section = new Section();
        section.setTitle(title);
        section.setDescription(description);
        sectionRepository.save(section);
        return "redirect:/admin/sections";
    }

    @GetMapping("/edit/{id}")
    public String showEditSectionForm(@PathVariable Long id, Model model) {
        Section section = sectionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid section Id:" + id));
        model.addAttribute("section", section);
        return "edit_section"; // шаблон для редактирования раздела
    }

    @PostMapping("/edit/{id}")
    public String editSection(@PathVariable Long id, @RequestParam String title, @RequestParam String description) {
        Section section = sectionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid section Id:" + id));
        section.setTitle(title);
        section.setDescription(description);
        sectionRepository.save(section);
        return "redirect:/admin/sections";
    }

    // Изменяем метод на POST для поддержки формы удаления
    @PostMapping("/delete/{id}")
    public String deleteSection(@PathVariable Long id) {
        sectionRepository.deleteById(id);
        return "redirect:/admin/sections";
    }
}
