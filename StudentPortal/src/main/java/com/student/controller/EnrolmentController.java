package com.student.controller;

import com.student.model.Student;
import com.student.service.EnrolmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EnrolmentController {

    private final EnrolmentService enrolmentService;

    public EnrolmentController(EnrolmentService enrolmentService) {
        this.enrolmentService = enrolmentService;
    }

    @RequestMapping("/enrolments")
    public ModelAndView enrolments(@RequestAttribute("student") Student student) {
        return enrolmentService.getEnrolments(student);
    }

}
