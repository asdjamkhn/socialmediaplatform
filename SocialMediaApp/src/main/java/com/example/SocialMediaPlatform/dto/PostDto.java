package com.example.SocialMediaPlatform.dto;

import com.example.SocialMediaPlatform.model.Comment;
import com.example.SocialMediaPlatform.model.Likes;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Setter
@Getter
public class PostDto {

    private LocalDate date = LocalDate.now();
    private String content;
    private List<Comment> commentList = new ArrayList<>();
    private List<Likes> likeList= new ArrayList<>();

}
