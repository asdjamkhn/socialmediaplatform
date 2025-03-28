package com.example.SocialMediaPlatform.service;

import com.example.SocialMediaPlatform.dto.PostDto;
import com.example.SocialMediaPlatform.model.Post;
import com.example.SocialMediaPlatform.model.User;
import com.example.SocialMediaPlatform.repository.PostRepository;
import com.example.SocialMediaPlatform.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post addPost(PostDto postDto) {

        User user = userRepository.findById(postDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException());

        Post post = new Post();
        post.setUser(user);
        post.setContent(postDto.getContent());

        return postRepository.save(post);
    }

    public Page<Post> findAll(Pageable pageable) {

        return postRepository.findAll(pageable);
    }
}
