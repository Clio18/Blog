package com.luxcampus.Blog.service;

import com.luxcampus.Blog.entity.Post;

import java.util.List;

public interface PostServiceInterface {

    List<Post> getAll();

    void save(Post post);

    void delete(int id);

    void update(Post post);

    Post findById(int id);
}
