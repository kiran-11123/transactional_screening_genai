package com.example.transaction_screening.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.example.transaction_screening.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private long expiration;


    private SecretKey getSigningKey(){
           return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

          public String generateToken(User user) {

      
                 

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("role",user.getRole())
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + expiration)
                )
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {

        return extractAllClaims(token)
                .getSubject();
    }

        public String extractEmail(String token) {

                return extractAllClaims(token)
                                .get("email", String.class);
        }

        public String extractRole(String token){
             return extractAllClaims(token).get("role" , String.class);
        }

        public String extractUserId(String token){
                 return  extractAllClaims(token).get("id" , String.class);
        }

      private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

      private boolean isTokenExpired(String token) {

        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    // Validate token
    public boolean isTokenValid(
            String token,
            String username_e
    ) {

        String username = extractUsername(token);

        return username.equals(username_e)
                && !isTokenExpired(token);
    }




}
