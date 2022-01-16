package com.luxcampus.Blog.repository;
import com.luxcampus.Blog.entity.Post;
import java.util.List;

public interface PostRepository {
    List<Post> getAll();

    boolean save(Post post);

    boolean delete(int id);

    boolean update(Post post);

    Post getById(int id);
}
