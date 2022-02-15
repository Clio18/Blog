package com.luxcampus.Blog.service;

import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.entity.Tag;
import com.luxcampus.Blog.repository.PostRepository;
import com.luxcampus.Blog.repository.TagRepository;
import com.luxcampus.Blog.service.impl.PostService;
import com.luxcampus.Blog.service.impl.TagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private PostService postService;
    @Mock
    private TagRepository tagRepository;

    @Test
    @DisplayName(value = "Test add tag to post and checking save and get by id (tagRepository, postService) methods calling")
    void addTagToPost() {
        Post one = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .tags(new HashSet<Tag>())
                .build();
        Optional<Post> post = Optional.of(one);

        when(postService.findById(1L)).thenReturn(post);

        TagService tagService = new TagService(postService, tagRepository);

        Tag tag = Tag.builder()
                .id(1L)
                .name("MU")
                .posts(new ArrayList<Post>())
                .build();

        when(tagRepository.save(tag)).thenReturn(tag);

        tagService.addTagToPost(tag, 1L);

        verify(tagRepository, times(1)).save(any());
        verify(postService, times(1)).findById(1L);
    }


    @Test
    @DisplayName(value = "Test delete tag from post and checking delete and find all (tagRepository, postService) methods calling")
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

        when(postService.findAll()).thenReturn(postsWithTags);
        when(tagRepository.findById(1L)).thenReturn(optionalTag);

        TagService tagService = new TagService(postService, tagRepository);

        tagService.deleteTagFromPost(1L);

        verify(tagRepository, times(1)).delete(tag1);
        verify(postService, times(1)).findAll();
    }


}