package com.example.SocialMediaPlatform.dto;

import com.example.SocialMediaPlatform.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class FollowDto {

    private int followerId;
    private int followingId;

}
