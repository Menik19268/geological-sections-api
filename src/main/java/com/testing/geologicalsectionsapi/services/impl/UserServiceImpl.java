package com.testing.geologicalsectionsapi.services.impl;

import com.testing.geologicalsectionsapi.repositories.UserRepository;
import com.testing.geologicalsectionsapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Override
//    public User registerUser(User user) {
//        return userRepository.save(user);
//    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.testing.geologicalsectionsapi.models.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_USER") // You can set roles/authorities here
                .build();
    }

}


