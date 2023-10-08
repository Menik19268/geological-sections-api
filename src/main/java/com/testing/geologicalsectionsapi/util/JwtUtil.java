package com.testing.geologicalsectionsapi.util;

import com.testing.geologicalsectionsapi.configurations.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final String jwtSecret;
    private final Long jwtExpiration;

    @Autowired
    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtSecret = jwtConfig.getJwtSecret();
        this.jwtExpiration = jwtConfig.getJwtExpiration();
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
}

