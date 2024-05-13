package com.student.controller;


import com.student.model.Student;
import com.student.model.User;
import com.student.repository.StudentRepository;
import com.student.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class StudentInterceptor implements HandlerInterceptor {

    private final UserService userService;
    private final StudentRepository studentRepository;
    private Student student;
    private User user;

    public StudentInterceptor(UserService userService, StudentRepository studentRepository) {
        this.userService = userService;
        this.studentRepository = studentRepository;
    }
    // This method is executed before the actual handler method of the controller is called.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        findUserAndStudent();
        request.setAttribute("user", user);
        request.setAttribute("student", student);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
    // This method is executed after the handler method is called but before the view is rendered.
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        findUserAndStudent();
        request.setAttribute("isStudent", student != null);
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    // Helper method to find the logged-in user and associated student
    private void findUserAndStudent() {
        user = userService.getLoggedInUser();
        if (user != null) {
            student = studentRepository.findByUserId(user.getId());
        }
    }
}