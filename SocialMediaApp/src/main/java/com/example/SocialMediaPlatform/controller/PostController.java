package com.example.SocialMediaPlatform.controller;

import com.example.SocialMediaPlatform.apiresponse.ApiResponse;
import com.example.SocialMediaPlatform.dto.CommentDto;
import com.example.SocialMediaPlatform.dto.LikesDto;
import com.example.SocialMediaPlatform.dto.PostDto;
import com.example.SocialMediaPlatform.model.Comment;
import com.example.SocialMediaPlatform.model.Likes;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
            @RequestParam(defaultValue = "postId") String sortBy,
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

    @GetMapping(Utils.POST_BY_ID)
    public ApiResponse getPostById(@PathVariable int id) {
        Optional<Post> posts = postService.getPostById(id);
        if (posts.isPresent()) {
            return new ApiResponse(Utils.POST_FOUND, HttpStatus.OK.value(), posts);
        } else {
            return new ApiResponse(Utils.POST_NOT_FOUND, HttpStatus.NOT_FOUND.value(), null);
        }
    }

    @PutMapping(Utils.POST_BY_ID)
    public ApiResponse updatePost(@PathVariable int id,@Valid @RequestBody PostDto postDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return new ApiResponse(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }

        boolean result = postService.updatePost(id, postDto);
        if (result) {
            return new ApiResponse(Utils.POST_UPDATED, HttpStatus.FOUND.value(), result);
        } else {
            return new ApiResponse(Utils.POST_FAILED_TO_UPDATE, HttpStatus.NOT_FOUND.value(), null);
        }
    }

    @DeleteMapping(Utils.POST_BY_ID)
    public ApiResponse deletePost(@PathVariable int id) {

        boolean result = postService.deletePost(id);
        if (result) {
            return new ApiResponse(Utils.POST_DELETED, HttpStatus.FOUND.value(), result);
        } else {
            return new ApiResponse(Utils.POST_FAILED_TO_DELETE, HttpStatus.NOT_FOUND.value(), result);
        }
    }

    @PostMapping(Utils.ADD_COMMENT)
    public ApiResponse addCommentToPost(@PathVariable int id, @Valid  @RequestBody CommentDto commentDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return new ApiResponse(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }
        Comment result = postService.addCommentToPost(id, commentDto);

        if (result != null) {
            return new ApiResponse(Utils.COMMENT_ADDED, HttpStatus.FOUND.value(), result);
        } else {
            return new ApiResponse(Utils.COMMENT_NOT_ADDED, HttpStatus.NOT_FOUND.value(), null);
        }
    }

    @PostMapping(Utils.LIKE_POST)
    public ApiResponse likePost(@PathVariable int id, @Valid @RequestBody LikesDto likesDto) {

        Likes result = postService.likePost(id, likesDto);

        if (result != null) {
            return new ApiResponse(Utils.LIKES_ADDED, HttpStatus.FOUND.value(), result);
        } else {
            return new ApiResponse(Utils.LIKES_NOT_ADDED, HttpStatus.NOT_FOUND.value(), null);
        }
    }

    @PostMapping(Utils.SEARCH_POSTS)
    public ApiResponse searchPosts(
            @RequestParam String content,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "content") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending){

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Post> page1  = postService.searchPosts(content, pageable);

        if (page1 != null) {
            return new ApiResponse(Utils.POSTS_SEARCHED, HttpStatus.FOUND.value(), page1);
        } else {
            return new ApiResponse(Utils.POSTS_NOT_FOUND, HttpStatus.NOT_FOUND.value(), page1);
        }
    }
}

