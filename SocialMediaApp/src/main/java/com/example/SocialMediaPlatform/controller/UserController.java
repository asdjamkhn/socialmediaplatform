package com.example.SocialMediaPlatform.controller;

import com.example.SocialMediaPlatform.apiresponse.ApiResponse;
import com.example.SocialMediaPlatform.dto.FollowDto;
import com.example.SocialMediaPlatform.dto.LoginDto;
import com.example.SocialMediaPlatform.dto.RegisterDto;
import com.example.SocialMediaPlatform.model.Follow;
import com.example.SocialMediaPlatform.model.Post;
import com.example.SocialMediaPlatform.model.User;
import com.example.SocialMediaPlatform.service.UserService;
import com.example.SocialMediaPlatform.util.Utils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        String result = null;
        try {
            result = userService.loginUser(loginDto);
        }catch (EntityNotFoundException entityNotFoundException){
            return new ApiResponse(Utils.INCORRECT_EMAIL, HttpStatus.NOT_FOUND.value(), result);
        }
        catch (RuntimeException runtimeException){
            return new ApiResponse(Utils.INCORRECT_PASSWORD, HttpStatus.NOT_FOUND.value(),result);
        }
        if (result != null) {
            return new ApiResponse(Utils.LOGIN_SUCCESSFULL, HttpStatus.FOUND.value(), result);
        } else {
            return new ApiResponse(Utils.LOGIN_FAILED, HttpStatus.NOT_FOUND.value(), result);
        }
    }

    @PostMapping("/users/login")
    public ApiResponse login(@RequestBody LoginDto loginDto) {
        try {
            String jwtToken = userService.login(loginDto.getEmail(), loginDto.getPassword());
            return new ApiResponse(Utils.LOGIN_SUCCESSFULL, HttpStatus.FOUND.value(), jwtToken);
        } catch (Exception e) {
            return new ApiResponse(Utils.LOGIN_FAILED, HttpStatus.NOT_FOUND.value(), null);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse userById(@PathVariable int id) {

        Optional<User> result = userService.userById(id);

        if (result.isPresent()) {
            return new ApiResponse(Utils.USER_FOUND, HttpStatus.FOUND.value(), result);
        } else {
            return new ApiResponse(Utils.USERS_NOT_FOUND, HttpStatus.NOT_FOUND.value(), null);
        }
    }

    @PostMapping(Utils.FOLLOW_USER)
    public ApiResponse followUser(@PathVariable int id, @RequestBody FollowDto followDto){

        boolean result = userService.followUser(id, followDto);

        if (result) {
            return new ApiResponse(Utils.USER_FOLLOWED, HttpStatus.OK.value(), result);
        } else {
            return new ApiResponse(Utils.USER_NOT_FOLLOWED, HttpStatus.NOT_FOUND.value(), result);
        }
    }

    @GetMapping(Utils.USERS_FOLLOWERS)
    public ApiResponse getUsersFollowers(@PathVariable int id){

        List<Follow> result = userService.getUsersFollowers(id);

        if (result != null) {
            return new ApiResponse(Utils.USER_FOLLOWERS, HttpStatus.OK.value(), result);
        } else {
            return new ApiResponse(Utils.USERS_FOLLOWERS_NOT_FOUND, HttpStatus.NOT_FOUND.value(), result);
        }
    }

    @GetMapping(Utils.USERS_FOLLOWING)
    public ApiResponse getUsersFollowing(@PathVariable int id){

        List<Follow> result = userService.getUsersFollowing(id);

        if (result != null) {
            return new ApiResponse(Utils.USER_FOLLOWING, HttpStatus.OK.value(), result);
        } else {
            return new ApiResponse(Utils.USERS_FOLLOWING_NOT_FOUND, HttpStatus.NOT_FOUND.value(), result);
        }
    }

    @PostMapping(Utils.SEARCH_USERS)
    public ApiResponse searchUsers(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending){

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> page1  = userService.searchUsers(email, pageable);

        if (page1 != null) {
            return new ApiResponse(Utils.USERS_SEARCHED, HttpStatus.FOUND.value(), page1);
        } else {
            return new ApiResponse(Utils.USERS_NOT_FOUND, HttpStatus.NOT_FOUND.value(), page1);
        }
    }
}