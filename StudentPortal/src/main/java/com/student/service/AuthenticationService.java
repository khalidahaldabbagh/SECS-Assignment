package com.student.service;


import com.student.model.Role;
import com.student.model.User;
import com.student.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
@Component
public class AuthenticationService {
    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String showLoginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    public ModelAndView registerNewUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.STUDENT);
        String errors = getValidationErrors(user);
        if (!errors.isEmpty()) {
            ModelAndView modelAndView = new ModelAndView("register");
            modelAndView.addObject("user", user);
            modelAndView.addObject("message", errors);
            return modelAndView;
        }
        userRepository.save(user);
        return new ModelAndView("register-success");
    }

    private String getValidationErrors(User user) {
        StringBuilder builder = new StringBuilder();
        boolean valid = true;
        if (!isValidUsername(user)) {
            builder.append("There is already an account with the username: ")
                    .append(user.getUserName());
            valid = false;
        }
        if (!isValidEmail(user)) {
            if (!valid) {
                builder.append(" and ");
            } else {
                builder.append("There is already an account with ");
            }
            builder.append("the e-mail address: ")
                    .append(user.getEmail());
            valid = false;
        }
        if (!valid) {
            builder.append(".");
        }
        return builder.toString();
    }

    private boolean isValidEmail(User user) {
     List<User>  users= userRepository.findByEmail(user.getEmail());
        if (users.size() == 0) {
           return true;
                }else{
            return false;
        }

    }

    private boolean isValidUsername(User user) {
        return userRepository.findByUserName(user.getUserName()) == null;
    }
}
