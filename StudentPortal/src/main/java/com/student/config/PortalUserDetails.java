package com.student.config;

import com.student.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PortalUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Method to load user details by username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userName, password;
        List<GrantedAuthority> authorities;
        com.student.model.User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User details not found for the user : " + username);
        } else {
            userName = user.getUserName();
            password = user.getPassword();
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        }
        return new User(userName, password, authorities);
    }

}