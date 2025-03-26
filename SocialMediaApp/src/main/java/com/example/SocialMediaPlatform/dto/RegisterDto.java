package com.example.SocialMediaPlatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.PrimitiveIterator;

@Builder
@Setter
@Getter
public class RegisterDto {


    @NotEmpty(message = "Username is mandatory.")
    private String username;

    private String email;
    private String password;
    private String profile;
    private String bio;

}
