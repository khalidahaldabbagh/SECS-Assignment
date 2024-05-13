package com.student.controller;


import com.student.model.Student;
import com.student.model.User;
import com.student.service.ProfileService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @RequestMapping("/profile")
    public ModelAndView profile(@RequestAttribute("user") User user, @Nullable @RequestAttribute("student") Student student) {
        return profileService.getProfile(user, student, "profile");
    }

    @GetMapping("/editProfile/{id}")
    public ModelAndView editProfile(@PathVariable Long id) {
        return profileService.getProfileToEdit(id);
    }

    @PostMapping("/editProfile")
    public ModelAndView editProfile(Student student) {
        return profileService.editProfile(student);
    }

}
