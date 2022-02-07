package com.luxcampus.Blog.repository;

import com.luxcampus.Blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
}
