package com.example.stockholm.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class StudentProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private int lessonsCompleted;
    private int tasksCompleted;

    // Добавляем связь с Section
    @ManyToMany
    @JoinTable(
            name = "student_completed_sections",
            joinColumns = @JoinColumn(name = "student_progress_id"),
            inverseJoinColumns = @JoinColumn(name = "section_id")
    )
    private Set<Section> completedSections = new HashSet<>();

    public StudentProgress() {
    }

    public StudentProgress(User user, int lessonsCompleted, int tasksCompleted) {
        this.user = user;
        this.lessonsCompleted = lessonsCompleted;
        this.tasksCompleted = tasksCompleted;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLessonsCompleted() {
        return lessonsCompleted;
    }

    public void setLessonsCompleted(int lessonsCompleted) {
        this.lessonsCompleted = lessonsCompleted;
    }

    public int getTasksCompleted() {
        return tasksCompleted;
    }

    public void setTasksCompleted(int tasksCompleted) {
        this.tasksCompleted = tasksCompleted;
    }

    public Set<Section> getCompletedSections() {
        return completedSections;
    }

    public void setCompletedSections(Set<Section> completedSections) {
        this.completedSections = completedSections;
    }

    public void addCompletedSection(Section section) {
        this.completedSections.add(section);
        this.lessonsCompleted = completedSections.size(); // обновляем количество завершенных уроков
    }

    public void removeCompletedSection(Section section) {
        this.completedSections.remove(section);
        this.lessonsCompleted = completedSections.size(); // обновляем количество завершенных уроков
    }
}
