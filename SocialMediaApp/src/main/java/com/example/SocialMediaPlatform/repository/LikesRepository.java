package com.example.SocialMediaPlatform.repository;


import com.example.SocialMediaPlatform.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes,Integer> {
}
