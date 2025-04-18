package com.example.SocialMediaPlatform.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ObjectInputFilter;
import java.security.Key;

@Component
public class JwtUtil {

    @Autowired
    private Key key;

    public String generateToken(String email, int id){

        return Jwts.builder()
                .setSubject(email)
                .claim("id",id)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

    }

    public Claims verifyToken(String token){

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
