package com.devterin.socialmedia.controllers;

import com.devterin.socialmedia.dtos.request.CreateUserRequest;
import com.devterin.socialmedia.dtos.request.UpdateUserRequest;
import com.devterin.socialmedia.dtos.response.UserResponse;
import com.devterin.socialmedia.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        UserResponse user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId,
                                                   @RequestBody UpdateUserRequest request) {
        UserResponse user = userService.updateUser(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
