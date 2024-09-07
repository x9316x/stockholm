package com.example.stockholm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {

    @GetMapping("/student")
    public String studentPage() {
        return "student"; // Возвращаем имя шаблона student.html
    }
}
