package com.example.SocialMediaPlatform.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UserDto {

    private String username;
    private String email;
    private String password;
    private String profile;
    private String bio;

}

