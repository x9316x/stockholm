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

    @OneToMany(mappedBy = "studentProgress", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentCompletedSection> completedSections = new HashSet<>();

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

    public Set<StudentCompletedSection> getCompletedSections() {
        return completedSections;
    }

    public void setCompletedSections(Set<StudentCompletedSection> completedSections) {
        this.completedSections = completedSections;
    }

    public void addCompletedSection(StudentCompletedSection completedSection) {
        this.completedSections.add(completedSection);
        this.lessonsCompleted = (int) this.completedSections.stream().filter(StudentCompletedSection::isCompleted).count();
    }

    public void removeCompletedSection(StudentCompletedSection completedSection) {
        this.completedSections.remove(completedSection);
        this.lessonsCompleted = (int) this.completedSections.stream().filter(StudentCompletedSection::isCompleted).count();
    }
}
