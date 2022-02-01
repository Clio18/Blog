package com.luxcampus.Blog.service;

import com.luxcampus.Blog.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentServiceInterface {

    void save(Long postId, Comment comment);

    List<Comment> findCommentsByPostId(Long id);

    Comment findCommentByPostIdAndCommentId(Long postId, Long id);

    Optional<Comment> findById(Long id);
}
