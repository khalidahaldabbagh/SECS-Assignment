package com.student.service;

import com.student.model.Student;
import com.student.model.User;
import com.student.repository.UserRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.ModelAndView;

@Validated
@Component
public class PortalService {

    private final UserRepository userRepository;

    public PortalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUserName(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found.");
//        }
//        return new PortalUserDetails(user);
//    }

    public ModelAndView loadPortalUserDetails(User user, Student student, @NotNull @NotEmpty String view) {
        if (user == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView modelAndView = new ModelAndView(view);
        modelAndView.addObject("user", user);
        if (student != null) {
            modelAndView.addObject("student", student);
        }
        modelAndView.addObject("showFirstName", student != null && student.getForename() != null);
        return modelAndView;
    }

}