package com.example.stockholm.repository;

import com.example.stockholm.model.Section;
import com.example.stockholm.model.StudentCompletedSection;
import com.example.stockholm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentCompletedSectionRepository extends JpaRepository<StudentCompletedSection, Long> {
    Optional<StudentCompletedSection> findByStudentAndSection(User student, Section section);
}
