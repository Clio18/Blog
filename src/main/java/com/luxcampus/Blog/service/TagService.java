package com.luxcampus.Blog.service;

import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.entity.Tag;
import com.luxcampus.Blog.repository.PostRepository;
import com.luxcampus.Blog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class TagService implements TagServiceInterface {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;


    @Transactional
    @Override
    public void addTagToPost(Tag tag, Long postId) {
        Post post = postRepository.getById(postId);

        List<Tag> tags = tagRepository.findAll();
        for (Tag existTag : tags) {
            if (tag.getName().equals(existTag.getName())) {
                post.getTags().add(existTag);
                existTag.getPosts().add(post);
                return;
            }
        }
        post.getTags().add(tag);
        tag.getPosts().add(post);
        tagRepository.save(tag);
    }

    @Override
    @Transactional
    public void deleteTagFromPost(Long id) {
        Tag tag = tagRepository.getById(id);

        List<Post> postList = postRepository.findAll();
        for (Post post : postList) {
            Set<Tag> tagSet = post.getTags();
            HashSet set = new HashSet(tagSet);
            set.remove(tag);
        }
        tagRepository.delete(tag);
    }
}
