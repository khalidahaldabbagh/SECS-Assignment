package com.student.controller;

import com.student.model.User;
import com.student.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping("/courses")
    public ModelAndView courses() {
        return courseService.getCourses();
    }

    @RequestMapping("/courses/{id}")
    public ModelAndView showCourse(@PathVariable Long id, @RequestAttribute("user") User user) {
        return courseService.getCourse(id, user);
    }

    @RequestMapping("/courses/{id}/enrol")
    public ModelAndView enrolInCourse(@PathVariable Long id, @RequestAttribute("user") User user) {
        return courseService.enrolInCourse(id, user);
    }

    @PostMapping("/courses/search")
    public ModelAndView searchCourses(@RequestParam String searchString) {
        return courseService.searchCourses(searchString);
    }
}