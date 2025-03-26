package com.example.SocialMediaPlatform.dto;

import com.example.SocialMediaPlatform.util.Utils;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.DataTruncation;

@Builder
@Setter
@Getter
public class LoginDto {

    @NotNull(message = Utils.NOT_NULL)
    @NotEmpty(message = Utils.NOT_EMPTY)
    private String email;

    @NotNull(message = Utils.NOT_NULL)
    @NotEmpty(message = Utils.NOT_EMPTY)
    private String password;

}
