package com.ev.batteryswap.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    public JwtAuthFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  throws ServletException, IOException  {
        if (request.getCookies() != null) { // kiểm tra trong request có cookies không
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName());
            }
        } else {
            System.out.println("Cookies is null");
        }
        filterChain.doFilter(request, response); // Cho phép request đi tiếp nếu hợp lệ
    }



}
