package com.example.SocialMediaPlatform.service;

import com.example.SocialMediaPlatform.dto.CommentDto;
import com.example.SocialMediaPlatform.model.Comment;
import com.example.SocialMediaPlatform.model.Post;
import com.example.SocialMediaPlatform.model.User;
import com.example.SocialMediaPlatform.repository.CommentRepository;
import com.example.SocialMediaPlatform.repository.PostRepository;
import com.example.SocialMediaPlatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    @Autowired
    private final CommentRepository commentRepository;

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final UserRepository userRepository;



    public List<Comment> getAllComments() {

        return commentRepository.findAll();

    }
}
