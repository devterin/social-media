package com.devterin.socialmedia.controllers;

import com.devterin.socialmedia.dtos.request.LoginRequest;
import com.devterin.socialmedia.dtos.response.LoginResponse;
import com.devterin.socialmedia.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        var authToken = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(authToken);
    }
}
