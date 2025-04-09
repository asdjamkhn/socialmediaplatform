package com.example.SocialMediaPlatform.controller;

import com.example.SocialMediaPlatform.apiresponse.ApiResponse;
import com.example.SocialMediaPlatform.dto.CommentDto;
import com.example.SocialMediaPlatform.model.Comment;
import com.example.SocialMediaPlatform.service.CommentService;
import com.example.SocialMediaPlatform.util.Utils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Utils.COMMENT_API_PREFIX)
public class CommentController {
    private final CommentService commentService;


    @GetMapping(Utils.FETCH_ALL_DATA)
    public ApiResponse getAllComments(){

        List<Comment> result = commentService.getAllComments();

        if (result != null) {
            return new ApiResponse(Utils.DATA_FETCHED, HttpStatus.OK.value(), result);
        } else {
            return new ApiResponse(Utils.DATA_FAILED_TO_FETCH, HttpStatus.NOT_FOUND.value(), null);
        }
    }
}
