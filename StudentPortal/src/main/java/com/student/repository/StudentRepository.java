package com.student.repository;
import com.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Inherit database interaction functionality from JpaRepository for Student class, of ID type Long
 * Create new Student *
 * Update existing Student *
 * Delete Student *
 * Find Student (one, all, or search by simple or complex properties)
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUserId(Long userId);
}
