package com.student.repository;
import com.student.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Inherit database interaction functionality from JpaRepository for Course class, of ID type Long
 * Create new Course *
 * Update existing Course *
 * Delete Course *
 * Find Course (one, all, or search by simple or complex properties)
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c FROM Course c WHERE CONCAT(c.title, c.description) LIKE %?1%")
    public List<Course> search(String keyword);
}
