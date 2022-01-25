package com.luxcampus.Blog.service;
import com.luxcampus.Blog.entity.Comment;
import com.luxcampus.Blog.entity.Post;

import java.util.List;

public interface CommentServiceInterface {


   void save(Long postId, Comment comment);

   List <Comment> findCommentsByPostId(Long id);

   Comment findCommentByPostIdAndCommentId(Long postId, Long id);
}
