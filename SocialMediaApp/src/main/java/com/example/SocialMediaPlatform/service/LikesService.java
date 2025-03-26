package com.example.SocialMediaPlatform.service;

import com.example.SocialMediaPlatform.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;

}
