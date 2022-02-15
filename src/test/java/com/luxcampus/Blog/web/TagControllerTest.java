package com.luxcampus.Blog.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.entity.Tag;
import com.luxcampus.Blog.service.impl.CommentService;
import com.luxcampus.Blog.service.impl.PostService;
import com.luxcampus.Blog.service.impl.TagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class TagControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostService postService;
    @MockBean
    private CommentService commentService;
    @MockBean
    private TagService tagService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName(value = "Test POST /api/v1/tags/{postId} and verify adding service work")
    void addTagToPost() throws Exception {
        Tag tag = Tag.builder()
                .name("sport")
                .id(1L)
                .posts(new ArrayList<Post>())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tags/1")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(tag)))
                .andExpect(status().isOk());

        verify(tagService, times(1)).addTagToPost(tag, 1L);
    }

    @Test
    @DisplayName(value = "Test DELETE /api/v1/tags/{id} and verify deleting service work")
    void deleteTagFromPost() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/tags/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(tagService, times(1)).deleteTagFromPost(1L);
    }

    @Test
    @DisplayName(value = "Test POST /api/v1/tags and verify getting all tags")
    void getAllTags() throws Exception {
        Tag tag1 = Tag.builder()
                .name("sport")
                .id(1L)
                .posts(new ArrayList<Post>())
                .build();
        Tag tag2 = Tag.builder()
                .name("box")
                .id(2L)
                .posts(new ArrayList<Post>())
                .build();
        List<Tag> tags = new ArrayList<>();
        tags.add(tag1);
        tags.add(tag2);

        when(tagService.getAllTags()).thenReturn(tags);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tags")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("sport"))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].name").value("box"))
                .andExpect(jsonPath("$[1].id").value(2L));

        verify(tagService, times(1)).getAllTags();
    }
}