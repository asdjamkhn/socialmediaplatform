package com.example.SocialMediaPlatform.service;

import com.example.SocialMediaPlatform.apiresponse.ApiResponse;
import com.example.SocialMediaPlatform.configuration.RabbitMQConfig;
import com.example.SocialMediaPlatform.dto.FollowDto;
import com.example.SocialMediaPlatform.dto.LoginDto;
import com.example.SocialMediaPlatform.dto.RegisterDto;
import com.example.SocialMediaPlatform.model.Follow;
import com.example.SocialMediaPlatform.model.Post;
import com.example.SocialMediaPlatform.model.User;
import com.example.SocialMediaPlatform.repository.FollowRepository;
import com.example.SocialMediaPlatform.repository.UserRepository;
import com.example.SocialMediaPlatform.util.JwtUtil;
import com.example.SocialMediaPlatform.util.Utils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileWriter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key.name}")
    private String routingKey;

    @Value("${rabbitmq.queue.name}")
    private String rmqQueue;

    public User addUser(RegisterDto registerDto) {
        try {
            User newUser = new User();
            newUser.setUsername(registerDto.getUsername());
            newUser.setEmail(registerDto.getEmail());
            newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            newUser.setBio(registerDto.getBio());

            User savedUser = userRepository.save(newUser);

            rabbitTemplate.convertAndSend(exchange, routingKey, savedUser.toString());


            return savedUser;
        } catch (
                Exception e) {
            log.error("Error: {}", e.getMessage());
            return null;
        }
    }

        public String loginUser (LoginDto loginDto){

            User user = userRepository.findByEmail(loginDto.getEmail())
                    .orElseThrow(() -> new EntityNotFoundException());

            // Validate password
            if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid password");
            }
            // Generate JWT with username only
            return jwtUtil.generateToken(user.getEmail(), user.getUserId());

        }

        public Optional<User> userById ( int id){

            return userRepository.findById(id);
        }

        public boolean followUser ( int followingId, FollowDto followDto){

            Optional<User> followingUser = userRepository.findById(followingId);
            Optional<User> followerUser = userRepository.findById(followDto.getFollowerId());

            if (!followerUser.isPresent() && !followingUser.isPresent()) {
                return false;
            }

            Follow follow = new Follow();
            follow.setFollower(followerUser.get());
            follow.setFollowing(followingUser.get());

            followRepository.save(follow);

            return true;
        }

        public List<Follow> getUsersFollowers ( int id){

            List<Follow> followList = followRepository.getUsersFollowers(id);

            if (followList.isEmpty()) {
                return null;
            } else {
                return followList;
            }
        }

        public List<Follow> getUsersFollowing ( int id){

            List<Follow> followList = followRepository.getUsersFollowing(id);

            if (followList.isEmpty()) {
                return null;
            } else {
                return followList;
            }
        }

        public Page<User> searchUsers (String email, Pageable pageable){

            Page<User> users = userRepository.searchUsers(email, pageable);

            return users;
        }

        public String login (String email, String password){

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Invalid username or password"));

            // Validate password
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new RuntimeException("Invalid username or password");
            }
            // Generate JWT with useremail and userId
            return jwtUtil.generateToken(user.getEmail(), user.getUserId());
        }
    }



