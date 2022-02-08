package com.luxcampus.Blog.service;

import com.luxcampus.Blog.entity.Tag;

import java.util.List;

public interface TagServiceInterface {
    void addTagToPost(Tag tag, Long postId);

    void deleteTagFromPost(Long id);

    List<Tag> getAllTags();
}
