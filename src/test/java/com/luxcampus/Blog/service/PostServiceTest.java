package com.luxcampus.Blog.service;

import com.luxcampus.Blog.entity.Comment;
import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.entity.Tag;
import com.luxcampus.Blog.entity.dto.PostWithCommentsDto;
import com.luxcampus.Blog.repository.PostRepository;
import com.luxcampus.Blog.service.impl.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    Post one = Post.builder()
            .id(1L)
            .title("news")
            .content("bbc.com")
            .comments(new ArrayList<Comment>())
            .tags(new HashSet<Tag>())
            .build();
    Post two = Post.builder()
            .id(2L)
            .title("news")
            .content("ccc.com")
            .comments(new ArrayList<Comment>())
            .tags(new HashSet<Tag>())
            .build();
    List<Post> list = List.of(one, two);

    @Test
    @DisplayName(value = "Test get all posts and return defined list of posts")
    void testGetAll() {
        PostService postService = new PostService(postRepository);
        when(postRepository.findAll()).thenReturn(list);
        List<Post> actualPosts = postService.findAll();
        assertEquals(list.size(), actualPosts.size());
        assertEquals(list.get(1).getTitle(), actualPosts.get(1).getTitle());
        assertEquals(list.get(1).getContent(), actualPosts.get(1).getContent());
        assertEquals(list.get(1).getId(), actualPosts.get(1).getId());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    @DisplayName(value = "Test save post and check calling postRepository save method")
    void testSave() {
        PostService postService = new PostService(postRepository);
        postService.save(one);
        verify(postRepository, times(1)).save(one);
    }

    @Test
    @DisplayName(value = "Test delete post and check calling postRepository delete and getById methods")
    void testDelete() {
        when(postRepository.getById(1L)).thenReturn(one);
        PostService postService = new PostService(postRepository);
        postService.delete(1L);
        verify(postRepository, times(1)).delete(one);
        verify(postRepository, times(1)).getById(1L);
    }

    @Test
    @DisplayName(value = "Test update post and check calling postRepository save method")
    void testUpdate() {
        PostService postService = new PostService(postRepository);
        postService.update(2L, one);
        verify(postRepository, times(1)).save(one);
    }

    @Test
    @DisplayName(value = "Test find by id and return defined post")
    void testFindById() {
        PostService postService = new PostService(postRepository);
        Optional<Post> optionalPost = Optional.of(one);
        when(postRepository.findById(1L)).thenReturn(optionalPost);
        Optional<Post> optional = postService.findById(1L);
        Post actualPost = optionalPost.get();
        assertEquals(one.getTitle(), actualPost.getTitle());
        assertEquals(one.getContent(), actualPost.getContent());
        assertEquals(one.getId(), actualPost.getId());
        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName(value = "Test find by title and return defined post")
    void testFindByTitleIs() {
        PostService postService = new PostService(postRepository);
        when(postRepository.findByTitleIs("news")).thenReturn(list);
        List<PostWithCommentsDto> actualPosts = postService.findByTitleIs("news");
        assertEquals(list.size(), actualPosts.size());
        assertEquals(list.get(1).getTitle(), actualPosts.get(1).getTitle());
        assertEquals(list.get(1).getContent(), actualPosts.get(1).getContent());
        assertEquals(list.get(1).getId(), actualPosts.get(1).getId());
        verify(postRepository, times(1)).findByTitleIs("news");
    }

    @Test
    @DisplayName(value = "Test order by title and return defined list of posts")
    void testFindByOrderByTitleAsc() {
        PostService postService = new PostService(postRepository);
        when(postRepository.findByOrderByTitleAsc()).thenReturn(list);
        List<PostWithCommentsDto> actualPosts = postService.findByOrderByTitleAsc();
        assertEquals(list.size(), actualPosts.size());
        assertEquals(list.get(1).getTitle(), actualPosts.get(1).getTitle());
        assertEquals(list.get(1).getContent(), actualPosts.get(1).getContent());
        assertEquals(list.get(1).getId(), actualPosts.get(1).getId());
        verify(postRepository, times(1)).findByOrderByTitleAsc();
    }

    @Test
    @DisplayName(value = "Test find by star and return defined list of posts")
    void testFindByStarTrue() {
        PostService postService = new PostService(postRepository);
        when(postRepository.findByStarTrue()).thenReturn(list);
        List<PostWithCommentsDto> actualPosts = postService.findByStarTrue();
        assertEquals(list.size(), actualPosts.size());
        assertEquals(list.get(1).getTitle(), actualPosts.get(1).getTitle());
        assertEquals(list.get(1).getContent(), actualPosts.get(1).getContent());
        assertEquals(list.get(1).getId(), actualPosts.get(1).getId());
        assertEquals(list.get(1).isStar(), actualPosts.get(1).isStar());
        verify(postRepository, times(1)).findByStarTrue();
    }

    @Test
    @DisplayName(value = "Test update post by set star true and return defined post")
    void testUpdatePostBySetStarTrue() {
        PostService postService = new PostService(postRepository);
        when(postRepository.updatePostBySetStarTrue(1L)).thenReturn(one);
        PostWithCommentsDto actualPost = postService.updatePostBySetStarTrue(1L);
        assertEquals(one.getTitle(), actualPost.getTitle());
        assertEquals(one.getContent(), actualPost.getContent());
        assertEquals(one.isStar(), actualPost.isStar());
        verify(postRepository, times(1)).updatePostBySetStarTrue(1L);
    }

    @Test
    @DisplayName(value = "Test update post by set star false and return defined post")
    void testUpdatePostBySetStarFalse() {
        PostService postService = new PostService(postRepository);
        when(postRepository.updatePostBySetStarFalse(1L)).thenReturn(one);
        PostWithCommentsDto actualPost = postService.updatePostBySetStarFalse(1L);
        assertEquals(one.getTitle(), actualPost.getTitle());
        assertEquals(one.getContent(), actualPost.getContent());
        assertEquals(one.isStar(), actualPost.isStar());
        verify(postRepository, times(1)).updatePostBySetStarFalse(1L);
    }
}