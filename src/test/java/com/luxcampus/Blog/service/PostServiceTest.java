package com.luxcampus.Blog.service;

import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Test
    void testGetAll() {
        PostService postService = new PostService(postRepository);
        Post one = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .build();
        Post two = Post.builder()
                .id(2L)
                .title("news")
                .content("ccc.com")
                .build();
        List<Post> list = List.of(one, two);

        when(postRepository.findAll()).thenReturn(list);

        List<Post> actualPosts = postService.getAll();
        assertEquals(list.size(), actualPosts.size());
        assertEquals(list.get(1).getTitle(), actualPosts.get(1).getTitle());
        assertEquals(list.get(1).getContent(), actualPosts.get(1).getContent());
        assertEquals(list.get(1).getId(), actualPosts.get(1).getId());

        verify(postRepository, times(1)).findAll();
    }

    @Test
    void testSave() {
        PostService postService = new PostService(postRepository);
        Post post = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .build();
        postService.save(post);

        verify(postRepository, times(1)).save(post);
    }

    @Test
    void testDelete() {
        PostService postService = new PostService(postRepository);
        postService.delete(1L);

        verify(postRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdate() {
        PostService postService = new PostService(postRepository);
        Post post = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .build();
        postService.update(2L, post);

        verify(postRepository, times(1)).save(post);
    }

    @Test
    void testFindById() {
        PostService postService = new PostService(postRepository);
        Post post = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .build();
        Optional<Post> optionalPost = Optional.of(post);
        when(postRepository.findById(1L)).thenReturn(optionalPost);

        Optional<Post> optional = postService.findById(1L);
        Post actualPost = optionalPost.get();
        assertEquals(post.getTitle(), actualPost.getTitle());
        assertEquals(post.getContent(), actualPost.getContent());
        assertEquals(post.getId(), actualPost.getId());

        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByTitleIs() {
        PostService postService = new PostService(postRepository);
        Post one = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .build();
        Post two = Post.builder()
                .id(2L)
                .title("news")
                .content("ccc.com")
                .build();
        List<Post> list = List.of(one, two);

        when(postRepository.findByTitleIs("news")).thenReturn(list);

        List<Post> actualPosts = postService.findByTitleIs("news");
        assertEquals(list.size(), actualPosts.size());
        assertEquals(list.get(1).getTitle(), actualPosts.get(1).getTitle());
        assertEquals(list.get(1).getContent(), actualPosts.get(1).getContent());
        assertEquals(list.get(1).getId(), actualPosts.get(1).getId());

        verify(postRepository, times(1)).findByTitleIs("news");

    }

    @Test
    void testFindByOrderByTitleAsc() {
        PostService postService = new PostService(postRepository);
        Post one = Post.builder()
                .id(1L)
                .title("news A")
                .content("bbc.com")
                .build();
        Post two = Post.builder()
                .id(2L)
                .title("news C")
                .content("ccc.com")
                .build();
        Post three = Post.builder()
                .id(3L)
                .title("news D")
                .content("ddd.com")
                .build();
        List<Post> list = List.of(one, two, three);

        when(postRepository.findByOrderByTitleAsc()).thenReturn(list);

        List<Post> actualPosts = postService.findByOrderByTitleAsc();
        assertEquals(list.size(), actualPosts.size());
        assertEquals(list.get(1).getTitle(), actualPosts.get(1).getTitle());
        assertEquals(list.get(1).getContent(), actualPosts.get(1).getContent());
        assertEquals(list.get(1).getId(), actualPosts.get(1).getId());

        verify(postRepository, times(1)).findByOrderByTitleAsc();
    }

    @Test
    void testFindByStarTrue() {
        PostService postService = new PostService(postRepository);

        Post one = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .star(true)
                .build();
        Post two = Post.builder()
                .id(2L)
                .title("news")
                .content("ccc.com")
                .star(true)
                .build();
        List<Post> list = List.of(one, two);

        when(postRepository.findByStarTrue()).thenReturn(list);

        List<Post> actualPosts = postService.findByStarTrue();
        assertEquals(list.size(), actualPosts.size());
        assertEquals(list.get(1).getTitle(), actualPosts.get(1).getTitle());
        assertEquals(list.get(1).getContent(), actualPosts.get(1).getContent());
        assertEquals(list.get(1).getId(), actualPosts.get(1).getId());
        assertEquals(list.get(1).isStar(), actualPosts.get(1).isStar());

        verify(postRepository, times(1)).findByStarTrue();

    }

    @Test
    void testUpdatePostBySetStarTrue() {
        PostService postService = new PostService(postRepository);
        Post one = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .star(true)
                .build();

        when(postRepository.updatePostBySetStarTrue(1L)).thenReturn(one);

        Post actualPost = postService.updatePostBySetStarTrue(1L);
        assertEquals(one.getTitle(), actualPost.getTitle());
        assertEquals(one.getContent(), actualPost.getContent());
        assertEquals(one.isStar(), actualPost.isStar());

        verify(postRepository, times(1)).updatePostBySetStarTrue(1L);

    }

    @Test
    void testUpdatePostBySetStarFalse() {
        PostService postService = new PostService(postRepository);
        Post one = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .star(false)
                .build();

        when(postRepository.updatePostBySetStarFalse(1L)).thenReturn(one);

        Post actualPost = postService.updatePostBySetStarFalse(1L);

        assertEquals(one.getTitle(), actualPost.getTitle());
        assertEquals(one.getContent(), actualPost.getContent());
        assertEquals(one.isStar(), actualPost.isStar());

        verify(postRepository, times(1)).updatePostBySetStarFalse(1L);
    }
}