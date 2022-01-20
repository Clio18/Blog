package com.luxcampus.Blog.web;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxcampus.Blog.entity.Post;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostService postService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName(value = "Test GET /api/v1/posts/1 and return defined post")
    void findById() throws Exception {
        Post post = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .build();
        when(postService.findById(1L))
                .thenReturn(post);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("news"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("bbc.com"));
    }

    @Test
    @DisplayName(value = "Test GET /api/v1/posts and return defined posts")
    void findAll() throws Exception {
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

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("news"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("bbc.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("sport"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content").value("football.com"));
    }

    @Test
    @DisplayName(value = "Test POST /api/v1/posts and verify the saving post")
    void save() throws Exception {
        Post post = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(post)))
                .andExpect(MockMvcResultMatchers
                        .status().isOk());

        verify(postService).save(any(Post.class));
    }

    @Test
    @DisplayName(value = "Test DELETE /api/v1/posts/1")
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/posts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isOk());
    }

    @Test
    @DisplayName(value = "Test PUT /api/v1/posts/1")
    void update() throws Exception {
        Post post = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/posts/1")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(post)))
                .andExpect(MockMvcResultMatchers
                        .status().isOk());

    }


}