package com.luxcampus.Blog.service.impl;

import com.luxcampus.Blog.entity.Comment;
import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.repository.CommentRepository;
import com.luxcampus.Blog.repository.PostRepository;
import com.luxcampus.Blog.service.CommentServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentServiceInterface {
    private final CommentRepository commentRepository;
    private final PostService postService;

    @Override
    public void save(Long postId, Comment comment) {
        Optional <Post> optionalPost = postService.findById(postId);
        comment.setPost(optionalPost.get());
        comment.setCreated_on(LocalDateTime.now());
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> findCommentsByPostId(Long postId) {
        return commentRepository.findCommentsByPostId(postId);
    }

    @Override
    public Comment findCommentByPostIdAndCommentId(Long postId, Long id) {
        return commentRepository.findCommentByPostIdAndCommentId(postId, id);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }
}
