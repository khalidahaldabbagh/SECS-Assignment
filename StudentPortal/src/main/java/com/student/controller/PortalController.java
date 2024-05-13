package com.student.controller;

import com.student.model.Student;
import com.student.model.User;
import com.student.service.PortalService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class PortalController {

    private final PortalService portalService;

    public PortalController(PortalService portalService) {
        this.portalService = portalService;
    }

    @RequestMapping("/home")
    public ModelAndView home(@RequestAttribute("user") User user, @Nullable @RequestAttribute("student") Student student) {
        return portalService.loadPortalUserDetails(user, student, "home");
    }

}
