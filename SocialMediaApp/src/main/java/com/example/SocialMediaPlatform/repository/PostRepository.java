package com.example.SocialMediaPlatform.repository;

import com.example.SocialMediaPlatform.model.Comment;
import com.example.SocialMediaPlatform.model.Follow;
import com.example.SocialMediaPlatform.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    @Query(value = "select * from post where content Like %:content%", nativeQuery = true)
    Page<Post> searchPosts(String content, Pageable pageable);

    @Query(value = "select * from post where user_id =:id", nativeQuery = true)
    List<Post> getPostByUserId(int id);

    @Query(value = "Select * from post where date > '2025-04-25';", nativeQuery = true)
    List<Post> getPostByGivenDate();

    @Query(value = "select distinct content from post", nativeQuery = true)
    List<Map<String,Object>> getUniquePost();

}
