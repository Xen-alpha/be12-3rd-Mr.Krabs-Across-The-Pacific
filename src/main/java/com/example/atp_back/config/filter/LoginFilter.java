package com.example.atp_back.config.filter;

import com.example.atp_back.user.model.SignupReq;
import com.example.atp_back.user.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token;
        try {
            SignupReq user = new ObjectMapper().readValue(request.getInputStream(), SignupReq.class);
            token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return authenticationManager.authenticate(token);
    }
}
