package com.luxcampus.Blog.service;

import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.entity.Tag;
import com.luxcampus.Blog.repository.PostRepository;
import com.luxcampus.Blog.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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


    @Test
    void deleteTagFromPost() {
        Tag tag1 = Tag.builder()
                .id(1L)
                .name("MU")
                .posts(new ArrayList<Post>())
                .build();

        Tag tag2 = Tag.builder()
                .id(2L)
                .name("Juve")
                .posts(new ArrayList<Post>())
                .build();

        Post one = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .tags(new HashSet<Tag>())
                .build();

        Post two = Post.builder()
                .id(2L)
                .title("news")
                .content("bbc.com")
                .tags(new HashSet<Tag>())
                .build();

        tag1.getPosts().add(one);
        tag1.getPosts().add(two);
        tag2.getPosts().add(one);
        tag2.getPosts().add(two);

        one.getTags().add(tag1);
        one.getTags().add(tag2);
        two.getTags().add(tag1);
        two.getTags().add(tag2);

        List<Post> postsWithTags = List.of(one, two);

        Optional<Tag> optionalTag = Optional.of(tag1);

        when(postRepository.findAll()).thenReturn(postsWithTags);
        when(tagRepository.findById(1L)).thenReturn(optionalTag);

        TagService tagService = new TagService(postRepository, tagRepository);

        tagService.deleteTagFromPost(1L);

        verify(tagRepository, times(1)).delete(tag1);
        verify(postRepository, times(1)).findAll();
    }


}