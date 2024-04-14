package com.example.demo.secuirty.filter;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Arrays;

import static com.example.demo.secuirty.SecurityConstants.*;
import static com.example.demo.utils.SystemUtil.*;

public class JWTAuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authToken = getToken(request, AUTH_TOKEN);

        Boolean byPass = request.getRequestURI().contains(AUTHENTICATE_PATH) ||
                request.getRequestURI().contains(SWAGGER_CONFIG) || request.getRequestURI().contains(H2_DATABASE) ||
                request.getRequestURI().contains(SWAGGER);
        if (byPass) {
            filterChain.doFilter(request, response);
            return;
        }
        if (authToken == null) {
            sendResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Authentication Required");
            return;
        }

        String code = JWT.require(Algorithm.HMAC512(SECRET_KEY))
                .build()
                .verify(authToken)
                .getSubject();

        Authentication authentication = new UsernamePasswordAuthenticationToken(code, null, Arrays.asList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

    }

    private String getToken(HttpServletRequest request, String tokenName) {
        Cookie cookie = WebUtils.getCookie(request, tokenName);
        return cookie != null ? cookie.getValue() : null;
    }
}