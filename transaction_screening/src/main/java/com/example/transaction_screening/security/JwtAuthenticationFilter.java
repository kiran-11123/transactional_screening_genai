package com.example.transaction_screening.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        log.info("JWT FILTER CALLED: {}", request.getRequestURI());

        String token = extractTokenFromCookie(request);

        // No JWT cookie
        if (token == null) {

            log.warn("JWT COOKIE NOT FOUND");

            filterChain.doFilter(request, response);
            return;
        }

        log.info("JWT COOKIE FOUND");

        try {

            String username = jwtService.extractUsername(token);
            String email = jwtService.extractEmail(token);
            String role = jwtService.extractRole(token);

            log.info("Username from JWT: {}", username);
            log.info("Email from JWT: {}", email);
            log.info("Role from JWT: {}", role);

            if (username != null
                    && SecurityContextHolder
                            .getContext()
                            .getAuthentication() == null) {

                boolean valid =
                        jwtService.isTokenValid(token, username);

                log.info("JWT VALID: {}", valid);

                if (valid) {

                    JwtPayloadDetails details =
                            new JwtPayloadDetails(
                                    email,
                                    username,
                                    role
                            );

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    details,
                                    null,
                                    details.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);

                    log.info(
                            "Authentication successfully created for user: {}",
                            username
                    );
                }
            }

        } catch (Exception e) {

            log.error("JWT authentication failed", e);

            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookie(
            HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {

            log.warn("Request contains no cookies");

            return null;
        }

        for (Cookie cookie : cookies) {

            log.info(
                    "Cookie received: {}",
                    cookie.getName()
            );

            if ("jwt".equals(cookie.getName())) {

                return cookie.getValue();
            }
        }

        return null;
    }
}