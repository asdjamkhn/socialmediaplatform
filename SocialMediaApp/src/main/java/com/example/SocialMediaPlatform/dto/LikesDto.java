package com.example.SocialMediaPlatform.dto;

import com.example.SocialMediaPlatform.model.Post;
import com.example.SocialMediaPlatform.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Setter
@Getter
public class LikesDto {

    private int postId;
    private int userId;

}
