package com.devterin.socialmedia.configurations;

import com.devterin.socialmedia.entities.Role;
import com.devterin.socialmedia.entities.User;
import com.devterin.socialmedia.repositories.RoleRepository;
import com.devterin.socialmedia.repositories.UserRepository;
import com.devterin.socialmedia.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ApplicationInitConfig {
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String ADMIN_PASSWORD = "123";
    private static final String USER_USERNAME = "user";
    private static final String USER_EMAIL = "user@gmail.com";
    private static final String USER_PASSWORD = "123";

    @Bean
    public ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {

        return args -> {
            Role roleAdmin = roleRepository.findByName(AppConstants.ADMIN_ROLE).orElseGet(
                    () -> {
                        Role newRole = new Role();
                        newRole.setName(AppConstants.ADMIN_ROLE);
                        return roleRepository.save(newRole);
                    });

            Role roleUser = roleRepository.findByName(AppConstants.USER_ROLE)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName(AppConstants.USER_ROLE);
                        return roleRepository.save(newRole);
                    });

            if (!userRepository.existsByUsername(ADMIN_USERNAME)) {
                User admin = User.builder()
                        .username(ADMIN_USERNAME)
                        .email(ADMIN_EMAIL)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(Set.of(roleAdmin))
                        .build();
                userRepository.save(admin);
            }
            if (!userRepository.existsByUsername(USER_USERNAME)) {
                User admin = User.builder()
                        .username(USER_USERNAME)
                        .email(USER_EMAIL)
                        .password(passwordEncoder.encode(USER_PASSWORD))
                        .roles(Set.of(roleUser))
                        .build();
                userRepository.save(admin);
            }
            log.info("Application initialization completed .....");
        };
    }
}


