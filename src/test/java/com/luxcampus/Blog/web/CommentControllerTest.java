package com.luxcampus.Blog.web;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxcampus.Blog.entity.Comment;
import com.luxcampus.Blog.service.CommentService;
import com.luxcampus.Blog.service.PostService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentService commentService;
    @MockBean
    private PostService postService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findById() throws Exception {
        //prepare
        Comment one = Comment.builder()
                .id(1L)
                .text("aaaa")
                .build();
        Comment two = Comment.builder()
                .id(2L)
                .text("bbbb")
                .build();
        List<Comment> comments = Arrays.asList(one, two);

        when(commentService.findCommentsByPostId(1L))
                .thenReturn(comments);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/1/comments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].text").value("aaaa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].text").value("bbbb"));

        verify(commentService, times(1)).findCommentsByPostId(1L);

    }
}