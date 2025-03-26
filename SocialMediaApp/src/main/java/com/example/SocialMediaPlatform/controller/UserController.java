package com.example.SocialMediaPlatform.controller;

import com.example.SocialMediaPlatform.apiresponse.ApiResponse;
import com.example.SocialMediaPlatform.dto.LoginDto;
import com.example.SocialMediaPlatform.dto.RegisterDto;
import com.example.SocialMediaPlatform.model.User;
import com.example.SocialMediaPlatform.service.UserService;
import com.example.SocialMediaPlatform.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(Utils.USER_API_PREFIX)
public class UserController {
    private final UserService userService;

    //http://localhost:8090/user/register
    @PostMapping(Utils.REGISTER)
    public ApiResponse addUser(@Valid @RequestBody RegisterDto registerDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return new ApiResponse(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }
        User result = userService.addUser(registerDto);

        if (result != null) {
            return new ApiResponse("User Added", HttpStatus.FOUND.value(), result);
        } else {
            return new ApiResponse("User not Added", HttpStatus.NOT_FOUND.value(), result);
        }
    }

    //http://localhost:8090/user/login
    @PostMapping(Utils.LOGIN)
    public ApiResponse loginUser(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult){

        userService.loginUser(loginDto);
        return new ApiResponse("",0,null);
    }
}
