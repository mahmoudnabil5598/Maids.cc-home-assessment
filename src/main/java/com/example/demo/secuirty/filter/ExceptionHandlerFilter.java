package com.example.demo.secuirty.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.exceptions.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.example.demo.utils.SystemUtil.sendResponse;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (EntityNotFoundException e) {
            sendResponse(response, HttpServletResponse.SC_NOT_FOUND, "User doesn't exist");
        } catch (JWTVerificationException e) {
            sendResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Authentication Required");
        } catch (RuntimeException e) {
            e.printStackTrace();
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, "BAD REQUEST");
        }
    }
}