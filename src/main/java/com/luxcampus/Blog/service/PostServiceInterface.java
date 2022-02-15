package com.luxcampus.Blog.service;

import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.entity.dto.PostWithCommentsAndTagsDto;
import com.luxcampus.Blog.entity.dto.PostWithCommentsDto;
import com.luxcampus.Blog.entity.dto.PostWithoutCommentsDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostServiceInterface {
    List<PostWithCommentsDto> getAllPostsWithComments();
    PostWithCommentsDto findPostByIdWithComments(Long id);
    PostWithCommentsAndTagsDto findPostByIdWithCommentsAddTags(Long id);
    PostWithoutCommentsDto findPostByIdWithoutComments(Long id);
    List<PostWithCommentsDto> findByTitleIs(String title);
    List<PostWithCommentsDto> findByOrderByTitleAsc();
    List<PostWithCommentsDto> findByStarTrue();
    PostWithCommentsDto updatePostBySetStarTrue(Long id);
    PostWithCommentsDto updatePostBySetStarFalse(Long id);
    List<PostWithCommentsAndTagsDto> getPostsByTags(List<String> tags);
    void save(Post post);
    void delete(Long id);
    void update(Long id, Post post);
    Optional<Post> findById(Long id);
    List<Post> findAll();
}

