package com.example.SocialMediaPlatform.dto;

import com.example.SocialMediaPlatform.model.Comment;
import com.example.SocialMediaPlatform.model.Likes;
import com.example.SocialMediaPlatform.util.Utils;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = Utils.NOT_NULL)
    @NotEmpty(message = Utils.NOT_EMPTY)
    private String content;

    private int userId;

}
