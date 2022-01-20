package com.luxcampus.Blog.service;

import com.luxcampus.Blog.entity.Post;

import java.util.List;

public interface PostServiceInterface {

    List<Post> getAll();

    void save(Post post);

    void delete(Long id);

    void update(Long id, Post post);

    Post findById(Long id);
}
