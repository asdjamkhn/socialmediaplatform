package com.example.SocialMediaPlatform.service;

import com.example.SocialMediaPlatform.apiresponse.ApiResponse;
import com.example.SocialMediaPlatform.dto.FollowDto;
import com.example.SocialMediaPlatform.dto.LoginDto;
import com.example.SocialMediaPlatform.dto.RegisterDto;
import com.example.SocialMediaPlatform.model.Follow;
import com.example.SocialMediaPlatform.model.Post;
import com.example.SocialMediaPlatform.model.User;
import com.example.SocialMediaPlatform.repository.FollowRepository;
import com.example.SocialMediaPlatform.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;


    public User addUser(RegisterDto registerDto) {
        try {
            User newUser = new User();
            newUser.setUsername(registerDto.getUsername());
            newUser.setEmail(registerDto.getEmail());
            newUser.setPassword(registerDto.getPassword());
            newUser.setBio(registerDto.getBio());

            return userRepository.save(newUser);
        } catch (Exception e) {
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

    public boolean followUser(int followingId, FollowDto followDto) {

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

    public List<Follow> getUsersFollowers(int id) {

        List<Follow> followList = followRepository.getUsersFollowers(id);

        if (followList.isEmpty()) {
            return null;
        } else {
            return followList;
        }
    }

    public List<Follow> getUsersFollowing(int id) {

        List<Follow> followList = followRepository.getUsersFollowing(id);

        if (followList.isEmpty()) {
            return null;
        } else {
            return followList;
        }
    }

    public Page<User> searchUsers(String email, Pageable pageable) {

        Page<User> users = userRepository.searchUsers(email, pageable);

        return users;
    }
}



