package com.example.SocialMediaPlatform.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.DataTruncation;

@Builder
@Setter
@Getter
public class LoginDto {

    private String email;
    private String password;

}
