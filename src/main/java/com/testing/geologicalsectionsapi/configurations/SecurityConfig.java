package com.testing.geologicalsectionsapi.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@EnableWebSecurity
//@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                http
                        .antMatcher("/api/**") // Secure API paths
                        .authorizeRequests()
                        .antMatchers(HttpMethod.OPTIONS).permitAll() // Allow pre-flight requests
                        .anyRequest().authenticated()
                        .and()
                        .httpBasic(); // Use Basic Authentication
        }
}
