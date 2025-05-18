package com.devterin.socialmedia.services;

import com.devterin.socialmedia.dtos.request.CreateUserRequest;
import com.devterin.socialmedia.dtos.request.UpdateUserRequest;
import com.devterin.socialmedia.dtos.response.UserResponse;
import com.devterin.socialmedia.entities.Role;
import com.devterin.socialmedia.entities.User;
import com.devterin.socialmedia.mapper.UserMapper;
import com.devterin.socialmedia.repositories.RoleRepository;
import com.devterin.socialmedia.repositories.UserRepository;
import com.devterin.socialmedia.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserResponse createUser(CreateUserRequest request) {
        validateInput(request);

        Role userRole = roleRepository.findByName(AppConstants.USER_ROLE)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(AppConstants.USER_ROLE);
                    return roleRepository.save(newRole);
                });

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(userRole));

        return userMapper.toDto(userRepository.save(user));
    }

    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        userMapper.updateUserFromDto(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toDto(userRepository.save(user));
    }


    private void validateInput(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("User existed");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email existed");
        }
    }
}
