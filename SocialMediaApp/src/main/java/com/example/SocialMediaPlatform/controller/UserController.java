package com.example.SocialMediaPlatform.controller;

import com.example.SocialMediaPlatform.apiresponse.ApiResponse;
import com.example.SocialMediaPlatform.dto.LoginDto;
import com.example.SocialMediaPlatform.dto.RegisterDto;
import com.example.SocialMediaPlatform.model.User;
import com.example.SocialMediaPlatform.service.UserService;
import com.example.SocialMediaPlatform.util.Utils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
            return new ApiResponse(Utils.USER_ADDED, HttpStatus.FOUND.value(), result);
        } else {
            return new ApiResponse(Utils.USER_NOT_ADDED, HttpStatus.BAD_REQUEST.value(), result);
        }
    }

    //http://localhost:8090/user/login
    @PostMapping(Utils.LOGIN)
    public ApiResponse loginUser(@Valid @RequestBody LoginDto loginDto,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return new ApiResponse(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }
        User result = null;
        try {
            result = userService.loginUser(loginDto);
        }catch (EntityNotFoundException entityNotFoundException){
            return new ApiResponse(Utils.INCORRECT_EMAIL, HttpStatus.NOT_FOUND.value(), result);
        }
        catch (IllegalArgumentException illegalArgumentException){
            return new ApiResponse(Utils.INCORRECT_PASSWORD, HttpStatus.NOT_FOUND.value(),result);
        }
        if (result != null) {
            return new ApiResponse(Utils.LOGIN_SUCCESSFULL, HttpStatus.FOUND.value(), result);
        } else {
            return new ApiResponse(Utils.LOGIN_FAILED, HttpStatus.NOT_FOUND.value(), result);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse userById(@PathVariable int id) {

        Optional<User> result = userService.userById(id);

        if (result.isPresent()) {
            return new ApiResponse(Utils.USER_ADDED, HttpStatus.FOUND.value(), result);
        } else {
            return new ApiResponse(Utils.USER_NOT_ADDED, HttpStatus.NOT_FOUND.value(), null);
        }
    }


}