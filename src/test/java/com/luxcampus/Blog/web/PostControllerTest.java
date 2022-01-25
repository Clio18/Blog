package com.luxcampus.Blog.web;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.service.CommentService;
import com.luxcampus.Blog.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostService postService;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName(value = "Test GET /api/v1/posts/1 and return defined post")
    void testFindById() throws Exception {
        Post post = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .build();
        when(postService.findById(1L))
                .thenReturn(post);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("news"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("bbc.com"));

        verify(postService, times(1)).findById(1L);
    }


    @Test
    @DisplayName(value = "Test GET /api/v1/posts and return defined posts")
    void testFindAll() throws Exception {
        //prepare
        Post one = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .build();
        Post two = Post.builder()
                .id(2L)
                .title("sport")
                .content("football.com")
                .build();
        List<Post> posts = Arrays.asList(one, two);
        when(postService.getAll())
                .thenReturn(posts);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("news"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("bbc.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("sport"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content").value("football.com"));

        verify(postService, times(1)).getAll();
    }

    @Test
    @DisplayName(value = "Test POST /api/v1/posts and verify the saving post")
    void testSave() throws Exception {
        Post post = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isOk());

        verify(postService).save(any(Post.class));
        verify(postService, times(1)).save(post);
    }

    @Test
    @DisplayName(value = "Test DELETE /api/v1/posts/1")
    void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/posts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(postService, times(1)).delete(1L);
    }

    @Test
    @DisplayName(value = "Test PUT /api/v1/posts/1")
    void testUpdate() throws Exception {
        Post post = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/posts/1")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isOk());

        verify(postService, times(1)).update(1L, post);

    }

    @Test
    @DisplayName(value = "Test GET /api/v1/posts by title")
    void testFindByTitle() throws Exception {
        Post one = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .build();
        Post two = Post.builder()
                .id(2L)
                .title("news")
                .content("football.com")
                .build();

        List<Post> posts = Arrays.asList(one, two);

        when(postService.findByTitleIs("news"))
                .thenReturn(posts);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts?title=news")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("news"))
                .andExpect(jsonPath("$[0].content").value("bbc.com"))
                .andExpect(jsonPath("$[1].title").value("news"))
                .andExpect(jsonPath("$[1].content").value("football.com"));

        verify(postService, times(1)).findByTitleIs(any());
    }

    @Test
    @DisplayName(value = "Test GET /api/v1/posts/sort sort")
    void testSortByTitle() throws Exception {
        Post one = Post.builder()
                .id(1L)
                .title("B")
                .content("bbc.com")
                .build();
        Post two = Post.builder()
                .id(2L)
                .title("A")
                .content("football.com")
                .build();

        List<Post> posts = Arrays.asList(one, two);


        when(postService.findByOrderByTitleAsc())
                .thenReturn(posts);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts?sort=A")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(postService, times(1)).findByOrderByTitleAsc();
    }


    @Test
    void getAllPostsWithStar() throws Exception {
        Post one = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .star(true)
                .build();
        Post two = Post.builder()
                .id(2L)
                .title("sport")
                .content("football.com")
                .star(true)
                .build();
        List<Post> posts = Arrays.asList(one, two);
        when(postService.findByStarTrue())
                .thenReturn(posts);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/star")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("news"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("bbc.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].star").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("sport"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content").value("football.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].star").value(true));

        verify(postService, times(1)).findByStarTrue();
    }

    @Test
    void setValueForStarTrue() throws Exception {
        Post one = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .star(true)
                .build();
        when(postService.updatePostBySetStarTrue(1L)).thenReturn(one);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/posts/1/star")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("news"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("bbc.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.star").value(true));

        verify(postService, times(1)).updatePostBySetStarTrue(1L);

    }

    @Test
    void updatePostBySetStarFalse() throws Exception {
        Post one = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .star(false)
                .build();
        when(postService.updatePostBySetStarFalse(1L)).thenReturn(one);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/posts/1/star")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("news"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("bbc.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.star").value(false));

        verify(postService, times(1)).updatePostBySetStarFalse(1L);
    }
}