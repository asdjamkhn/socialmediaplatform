package com.example.SocialMediaPlatform.controller;

import com.example.SocialMediaPlatform.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikesController {
    private final LikesService likeService;
}
