package com.devterin.socialmedia.dtos.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
}
