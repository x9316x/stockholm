package com.example.stockholm.repository;

import com.example.stockholm.model.StudentProgress;
import com.example.stockholm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentProgressRepository extends JpaRepository<StudentProgress, Long> {
    Optional<StudentProgress> findByUser(User user);
}
