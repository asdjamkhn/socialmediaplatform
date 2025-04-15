package com.example.SocialMediaPlatform.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ObjectInputFilter;
import java.security.Key;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private Key key;

    public String generateToken(String email, int id){

        return Jwts.builder()
                .setSubject(email)
                .claim("id",id)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }

    public Claims verifyToken(String token){

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }


}
