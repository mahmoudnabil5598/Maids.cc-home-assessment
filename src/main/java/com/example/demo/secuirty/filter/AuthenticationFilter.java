package com.example.demo.secuirty.filter;

import com.example.demo.dtos.reponses.UserDTO;
import com.example.demo.dtos.requests.AuthRequest;
import com.example.demo.secuirty.manager.CustomAuthenticationManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

import static com.example.demo.secuirty.SecurityConstants.*;
import static com.example.demo.utils.SystemUtil.*;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private CustomAuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            AuthRequest user = new ObjectMapper().readValue(request.getInputStream(), AuthRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        sendResponse(response, HttpServletResponse.SC_UNAUTHORIZED, failed.getMessage());
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        UserDTO userDTO = new ObjectMapper().readValue(authResult.getName(), UserDTO.class);

        String authToken = createToken(userDTO.getUserName(), AUTH_TOKEN_EXPIRATION);

        response.addCookie(createCookie(authToken, AUTH_TOKEN, AUTH_TOKEN_PATH));

        sendResponse(response, HttpServletResponse.SC_OK, "Authentication is successfully");

    }


}