package com.example.stockholm.model;

import jakarta.persistence.*;

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
}
