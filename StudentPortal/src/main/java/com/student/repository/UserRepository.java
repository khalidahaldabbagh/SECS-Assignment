package com.student.repository;

import com.student.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Inherit database interaction functionality from JpaRepository for User class, of ID type Long
 * Create new User *
 * Update existing User *
 * Delete User *
 * Find User (one, all, or search by simple or complex properties)
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);
    List<User> findByEmail(String email);
}
