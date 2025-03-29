package com.example.SocialMediaPlatform.service;

import com.example.SocialMediaPlatform.dto.LoginDto;
import com.example.SocialMediaPlatform.dto.RegisterDto;
import com.example.SocialMediaPlatform.model.User;
import com.example.SocialMediaPlatform.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User addUser(RegisterDto registerDto) {
        try {
            User newUser = new User();
            newUser.setUsername(registerDto.getUsername());
            newUser.setEmail(registerDto.getEmail());
            newUser.setPassword(registerDto.getPassword());
            newUser.setBio(registerDto.getBio());

            return userRepository.save(newUser);
        } catch (Exception e){
            log.error("Error: {}", e.getMessage());
            return null;
        }
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
