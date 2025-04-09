package com.example.SocialMediaPlatform.repository;

import com.example.SocialMediaPlatform.model.Comment;
import com.example.SocialMediaPlatform.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Integer> {

    @Query(value = "select * from follow where following_id =:id", nativeQuery = true)
    List<Follow> getUsersFollowers(int id);

    @Query(value = "select * from follow where follower_id =:id", nativeQuery = true)
    List<Follow> getUsersFollowing(int id);
}



