package com.luxcampus.Blog.repository;
import com.luxcampus.Blog.entity.Post;
import java.util.List;

public interface PostRepository {
    List<Post> getAll();

    void save(Post post);

    void delete(int id);

    void update(int id, Post post);

    Post getById(int id);
}
