package com.example.SocialMediaPlatform.repository;

import com.example.SocialMediaPlatform.model.Comment;
import com.example.SocialMediaPlatform.model.Post;
import com.example.SocialMediaPlatform.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
    Optional<User> findByEmail(String email);

    @Query(value = "select * from user where email Like %:email%", nativeQuery = true)
    Page<User> searchUsers(String email, Pageable pageable);

}
