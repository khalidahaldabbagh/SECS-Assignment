package com.student.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration

public class ProjectSecurityConfig {

    // Configure the default security filter chain
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())// Disable CSRF protection
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/register/**","/css/**", "/js/**").permitAll() // Allow access to certain URLs without authentication
                        .anyRequest().authenticated()) // Require authentication for any other request
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                        .loginPage("/login") // Specify the login page URL
                        .usernameParameter("userName")// Specify the parameter name for the username
                        .defaultSuccessUrl("/home",true).permitAll())// Specify the default URL after successful login
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/logout.done") // Specify the URL after successful logout
                        .deleteCookies("JSESSIONID") // Delete cookies
                        .invalidateHttpSession(true)); // Invalidate the HTTP session
        return http.build();
    }
    // Bean definition for password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}