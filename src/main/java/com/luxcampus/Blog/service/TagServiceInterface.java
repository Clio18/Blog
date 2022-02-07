package com.luxcampus.Blog.service;

import com.luxcampus.Blog.entity.Tag;

public interface TagServiceInterface {
    void addTagToPost(Tag tag, Long postId);
    void deleteTagFromPost(Long id);

}
