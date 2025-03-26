package com.example.SocialMediaPlatform.repository;

import com.example.SocialMediaPlatform.model.Comment;
import com.example.SocialMediaPlatform.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
}
