package com.example.SocialMediaPlatform.repository;

import com.example.SocialMediaPlatform.model.Comment;
import com.example.SocialMediaPlatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

}
