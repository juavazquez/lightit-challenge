package com.lightit.challenge.config.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SecretTokenFilter extends OncePerRequestFilter {

    @Value("${app.auth.key}")
    private String secretToken;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // Simple authorization token check

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (secretToken.equals(token)) {
            filterChain.doFilter(request, response);
            System.out.println("Token is correct");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }

}
