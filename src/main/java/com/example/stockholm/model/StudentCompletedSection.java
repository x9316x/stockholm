package com.example.stockholm.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "student_completed_sections")
public class StudentCompletedSection implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_progress_id", nullable = false)
    private StudentProgress studentProgress;

    @Column(nullable = false)
    private boolean completed;

    public StudentCompletedSection() {
    }

    // Конструктор, который нужен для корректной работы
    public StudentCompletedSection(User student, Section section, StudentProgress studentProgress, boolean completed) {
        this.student = student;
        this.section = section;
        this.studentProgress = studentProgress;
        this.completed = completed;
    }

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public StudentProgress getStudentProgress() {
        return studentProgress;
    }

    public void setStudentProgress(StudentProgress studentProgress) {
        this.studentProgress = studentProgress;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
