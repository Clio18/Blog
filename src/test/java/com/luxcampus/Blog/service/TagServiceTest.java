package com.luxcampus.Blog.service;

import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.entity.Tag;
import com.luxcampus.Blog.repository.PostRepository;
import com.luxcampus.Blog.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private TagRepository tagRepository;

    @Test
    void addTagToPost() {
        Post one = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .tags(new HashSet<Tag>())
                .build();

        when(postRepository.getById(1L)).thenReturn(one);

        TagService tagService = new TagService(postRepository, tagRepository);

        Tag tag = Tag.builder()
                .id(1L)
                .name("MU")
                .posts(new ArrayList<Post>())
                .build();

        when(tagRepository.save(tag)).thenReturn(tag);

        tagService.addTagToPost(tag, 1L);

        verify(tagRepository, times(1)).save(any());
        verify(postRepository, times(1)).getById(1L);
    }
}