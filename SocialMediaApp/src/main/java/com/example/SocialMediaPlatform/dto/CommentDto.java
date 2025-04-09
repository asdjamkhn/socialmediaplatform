package com.example.SocialMediaPlatform.dto;

import com.example.SocialMediaPlatform.model.Comment;
import com.example.SocialMediaPlatform.model.Post;
import com.example.SocialMediaPlatform.model.User;
import com.example.SocialMediaPlatform.util.Utils;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Builder
@Setter
@Getter
public class CommentDto {

    @NotNull(message = Utils.NOT_NULL)
    @NotEmpty(message = Utils.NOT_EMPTY)
    private String content;

    private int userId;

}
