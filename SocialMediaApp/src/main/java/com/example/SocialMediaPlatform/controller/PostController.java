package com.example.SocialMediaPlatform.controller;

import com.example.SocialMediaPlatform.apiresponse.ApiResponse;
import com.example.SocialMediaPlatform.dto.PostDto;
import com.example.SocialMediaPlatform.model.Post;
import com.example.SocialMediaPlatform.service.PostService;
import com.example.SocialMediaPlatform.util.Utils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

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

    @GetMapping
    public ApiResponse getAllPost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> postPage = postService.findAll(pageable);

        if (!postPage.isEmpty()) {
            return new ApiResponse(Utils.POST_ADDED, HttpStatus.OK.value(), postPage);
        } else {
            return new ApiResponse(Utils.POST_NOT_ADDED, HttpStatus.BAD_REQUEST.value(), postPage);
        }
    }
}

