package com.example.SocialMediaPlatform.service;

import com.example.SocialMediaPlatform.dto.CommentDto;
import com.example.SocialMediaPlatform.dto.LikesDto;
import com.example.SocialMediaPlatform.dto.PostDto;
import com.example.SocialMediaPlatform.model.Comment;
import com.example.SocialMediaPlatform.model.Likes;
import com.example.SocialMediaPlatform.model.Post;
import com.example.SocialMediaPlatform.model.User;
import com.example.SocialMediaPlatform.repository.CommentRepository;
import com.example.SocialMediaPlatform.repository.LikesRepository;
import com.example.SocialMediaPlatform.repository.PostRepository;
import com.example.SocialMediaPlatform.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.descriptor.java.LocalDateJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;

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

    public Optional<Post> getPostById(int id) {
        try {
            return postRepository.findById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updatePost(int id, PostDto postDto) {

        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isPresent()) {
            Post newPost = postOptional.get();
            newPost.setContent(postDto.getContent());
            newPost.setDate(LocalDate.now());

            postRepository.save(newPost);

            return true;
        } else {
            return false;
        }
    }

    public boolean deletePost(int id) {

        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isPresent()) {

            postRepository.deleteById(optionalPost.get().getPostId());

            return true;
        } else {
            return false;
        }
    }

    public Comment addCommentToPost(int postId, CommentDto commentDto) {

        Optional<Post> post = postRepository.findById(postId);

        Optional<User> user = userRepository.findById(commentDto.getUserId());

        if (post.isPresent() && user.isPresent()) {

            Comment newComment = new Comment();
            newComment.setContent(commentDto.getContent());
            newComment.setPost(post.get());
            newComment.setUser(user.get());

            return commentRepository.save(newComment);
        } else {
            return null;
        }
    }

    public Likes likePost(int postId, LikesDto likesDto) {

        Optional<Post> post = postRepository.findById(postId);

        Optional<User> user = userRepository.findById(likesDto.getUserId());

        if (post.isPresent() && user.isPresent()) {

            Likes likes = new Likes();
            likes.setPost(post.get());
            likes.setUser(user.get());

            return likesRepository.save(likes);
        } else {
            return null;
        }
    }

    public Page<Post> searchPosts(String content, Pageable pageable) {

        Page<Post> postList = postRepository.searchPosts(content, pageable);

        return postList;
    }

    public List<Post> getPostByUserId(int id) {

        try {
            return postRepository.getPostByUserId(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Post> getPostByGivenDate() {

        try {
            return postRepository.getPostByGivenDate();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String,Object>> getUniquePost() {

        try {
            return postRepository.getUniquePost();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
