package com.testing.geologicalsectionsapi.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
//    User registerUser(User user);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
