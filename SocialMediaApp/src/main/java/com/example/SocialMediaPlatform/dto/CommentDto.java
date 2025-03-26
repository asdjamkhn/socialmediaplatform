package com.example.SocialMediaPlatform.dto;

import com.example.SocialMediaPlatform.model.Post;
import com.example.SocialMediaPlatform.model.User;
import lombok.*;

import java.time.LocalDate;

@Builder
@Setter
@Getter
public class CommentDto {

    private LocalDate date = LocalDate.now();
    private String content;
    private Post post;
    private User user;

}
