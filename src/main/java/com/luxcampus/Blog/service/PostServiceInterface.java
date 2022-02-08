package com.luxcampus.Blog.service;

import com.luxcampus.Blog.entity.Post;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostServiceInterface {

    List<Post> getAll();

    void save(Post post);

    void delete(Long id);

    void update(Long id, Post post);

    Optional<Post> findById(Long id);

    List<Post> findByTitleIs(String title);

    List<Post> findByOrderByTitleAsc();

    List<Post> findByStarTrue();

    Post updatePostBySetStarTrue(Long id);

    Post updatePostBySetStarFalse(Long id);

    Set<Post> getPostsByTags(List<String> tags);
}

