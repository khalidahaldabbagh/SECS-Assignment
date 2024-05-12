package com.student.service;


import com.student.exception.StudentAlreadyExistsException;
import com.student.model.Account;
import com.student.model.Student;
import com.student.model.User;
import com.student.repository.StudentRepository;
import com.student.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
public class UserService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final IntegrationService integrationService;
    SecurityContext securityContext;

    public UserService(StudentRepository studentRepository, UserRepository userRepository, IntegrationService integrationService) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.integrationService = integrationService;
    }

    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    // Method to get the currently logged-in user
    public User getLoggedInUser() {
        if (securityContext == null || securityContext.getAuthentication() == null) {
            setSecurityContext(SecurityContextHolder.getContext());
        }
        Authentication authentication = securityContext.getAuthentication();
        User user = null;
        // If authentication object exists and is authenticated, retrieve user details
        if (authentication != null && authentication.isAuthenticated()) {
            user = userRepository.findByUserName(authentication.getName());
        }
        return user;
    }

    public Student findStudentFromUser(@NotNull User user) {
        return studentRepository.findByUserId(user.getId());
    }

    public User createStudentFromUser(@NotNull User user) {
        if (studentRepository.findByUserId(user.getId()) != null) {
            throw new StudentAlreadyExistsException(user.getUserName());
        }
        Student student = new Student();
        student.populateStudentId();
        user.setStudent(student);
        userRepository.saveAndFlush(user);
        notifySubscribers(student);
        return user;
    }

    private void notifySubscribers(Student student) {
        Account account = new Account();
        account.setStudentId(student.getStudentId());
        integrationService.notifyStudentCreated(account);
    }
}
