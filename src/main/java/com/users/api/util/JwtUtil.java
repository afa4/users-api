package com.users.api.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${token.secret}")
    private String SECRET;

    public String generateToken(String email, String name, Date creationDate) {
        return Jwts.builder()
                .setSubject(email)
                .claim("name", name)
                .setIssuedAt(creationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
}
