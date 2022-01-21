package com.luxcampus.Blog.repository;
import com.luxcampus.Blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findByTitleIs(String title);

    List<Post> findByOrderByTitleAsc();
}
