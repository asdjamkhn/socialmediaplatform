package com.example.SocialMediaPlatform.controller;

import com.example.SocialMediaPlatform.apiresponse.ApiResponse;
import com.example.SocialMediaPlatform.dto.PostDto;
import com.example.SocialMediaPlatform.model.Post;
import com.example.SocialMediaPlatform.service.PostService;
import com.example.SocialMediaPlatform.util.Utils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(Utils.POST_API_PREFIX)
public class PostController {
    private final PostService postService;

    @PostMapping
    public ApiResponse addPost(@Valid @RequestBody PostDto postDto) {

        Post result = postService.addPost(postDto);

        if (result != null) {
            return new ApiResponse(Utils.POST_ADDED, HttpStatus.OK.value(), result);
        } else {
            return new ApiResponse(Utils.POST_NOT_ADDED, HttpStatus.BAD_REQUEST.value(), result);
        }
    }
}
