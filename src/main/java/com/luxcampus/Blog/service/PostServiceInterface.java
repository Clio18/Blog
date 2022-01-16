package com.luxcampus.Blog.service;
import com.luxcampus.Blog.entity.Post;
import java.util.List;

public interface PostServiceInterface {

    List<Post> getAll();

    boolean save(Post post);

    boolean delete(int id);

    boolean update(Post post);
}
