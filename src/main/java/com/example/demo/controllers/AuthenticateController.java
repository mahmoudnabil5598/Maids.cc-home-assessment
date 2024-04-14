package com.example.demo.controllers;


import com.example.demo.dtos.requests.AuthRequest;
import com.example.demo.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
@AllArgsConstructor
public class AuthenticateController extends BaseController {

    private AuthService authService;

    @PostMapping
    public ResponseEntity<Void> setCookie(@Valid @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> removeCookie(HttpServletRequest request, HttpServletResponse response) {

        authService.revoke(request, response);
        return ResponseEntity.noContent().build();
    }
}
