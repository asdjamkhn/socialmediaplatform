package com.example.SocialMediaPlatform.service;

import com.example.SocialMediaPlatform.dto.LoginDto;
import com.example.SocialMediaPlatform.dto.RegisterDto;
import com.example.SocialMediaPlatform.model.User;
import com.example.SocialMediaPlatform.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User addUser(RegisterDto registerDto) {

        User newUser = new User();
        newUser.setUsername(registerDto.getUsername());
        newUser.setEmail(registerDto.getEmail());
        newUser.setPassword(registerDto.getPassword());
        newUser.setBio(registerDto.getBio());

        return userRepository.save(newUser);
    }


    public User loginUser(LoginDto loginDto) {

        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException());

        if (!loginDto.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException();
        }
        return user;
    }

    public Optional<User> userById(int id) {

        return userRepository.findById(id);
    }
}
