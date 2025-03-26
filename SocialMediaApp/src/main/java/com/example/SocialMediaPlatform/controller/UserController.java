package com.example.SocialMediaPlatform.controller;

import com.example.SocialMediaPlatform.apiresponse.ApiResponse;
import com.example.SocialMediaPlatform.dto.RegisterDto;
import com.example.SocialMediaPlatform.dto.UserDto;
import com.example.SocialMediaPlatform.model.User;
import com.example.SocialMediaPlatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ApiResponse addUser(@Valid @RequestBody RegisterDto registerDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            String errorMessage = bindingResult.getFieldError()!= null?
                    bindingResult.getFieldError().getDefaultMessage():
                    "Validation failed";
            return ApiResponse.builder()
                    .message(errorMessage)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .data(null)
                    .build();
        }
        User result = userService.addUser(registerDto);

        if (result != null) {
            return ApiResponse.builder()
                    .message("User added")
                    .status(HttpStatus.FOUND.value())
                    .data(result)
                    .build();

        } else {
            return ApiResponse.builder()
                    .message("User not added")
                    .status(HttpStatus.NOT_FOUND.value())
                    .data(result)
                    .build();

        }
    }
}
