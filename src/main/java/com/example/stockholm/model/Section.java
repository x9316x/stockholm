package com.example.stockholm.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "sections")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    // Добавляем связь с StudentCompletedSection
    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentCompletedSection> completedSections;

    public Section() {
    }

    public Section(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<StudentCompletedSection> getCompletedSections() {
        return completedSections;
    }

    public void setCompletedSections(List<StudentCompletedSection> completedSections) {
        this.completedSections = completedSections;
    }
}
