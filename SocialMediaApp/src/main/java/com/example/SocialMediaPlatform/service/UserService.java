package com.example.SocialMediaPlatform.service;

import com.example.SocialMediaPlatform.dto.LoginDto;
import com.example.SocialMediaPlatform.dto.RegisterDto;
import com.example.SocialMediaPlatform.dto.UserDto;
import com.example.SocialMediaPlatform.model.User;
import com.example.SocialMediaPlatform.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User addUser(RegisterDto registerDto) {

        User newUser = new User();
        newUser.setUsername(registerDto.getUsername());
        newUser.setEmail(registerDto.getEmail());
        newUser.setPassword(registerDto.getPassword());
        newUser.setProfile(registerDto.getProfile());
        newUser.setBio(registerDto.getBio());

        return userRepository.save(newUser);
    }

    public User loginUser(LoginDto loginDto) {

        //if(loginDto.password is equal to user.password) && ( loginDto.email is equal to user.email)
        //return user
        return null;
    }
}
