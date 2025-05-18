package com.devterin.socialmedia.services;

import com.devterin.socialmedia.dtos.request.LoginRequest;
import com.devterin.socialmedia.dtos.response.LoginResponse;
import com.devterin.socialmedia.entities.User;
import com.devterin.socialmedia.mapper.AuthMapper;
import com.devterin.socialmedia.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthMapper authMapper;

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);

        var accessToken = jwtService.generateToken(new HashMap<>(), user.getUsername());
        var refreshToken = jwtService.generateRefreshToken(user.getUsername());

        return authMapper.toDto(accessToken, refreshToken);

    }
}
