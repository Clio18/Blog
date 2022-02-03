package com.luxcampus.Blog.service;

import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.entity.Tag;
import com.luxcampus.Blog.repository.PostRepository;
import com.luxcampus.Blog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TagService implements TagServiceInterface {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;


    @Override
    public void addTagToPost(Tag tag, Long postId) {
        Post post = postRepository.getById(postId);
        post.getTags().add(tag);
        tag.getPosts().add(post);
        tagRepository.save(tag);
    }
}
