package com.testing.geologicalsectionsapi.models;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Data
public class UserPrincipal {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static UserPrincipal create(User user) {
        return new UserPrincipal(
                user.getUsername(),
                user.getPassword()
        );
    }

}
