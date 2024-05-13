package com.student.controller;

import com.student.model.Student;
import com.student.service.GraduationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GraduationController {

    private final GraduationService graduationService;

    public GraduationController(GraduationService graduationService) {
        this.graduationService = graduationService;
    }

    @RequestMapping("/graduation")
    public ModelAndView home(@RequestAttribute("student") Student student) {
        return graduationService.getGraduationStatus(student);
    }

}
