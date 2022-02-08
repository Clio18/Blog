package com.luxcampus.Blog.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxcampus.Blog.entity.Comment;
import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.entity.Tag;
import com.luxcampus.Blog.service.CommentService;
import com.luxcampus.Blog.service.PostService;
import com.luxcampus.Blog.service.TagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostService postService;
    @MockBean
    private CommentService commentService;
    @MockBean
    private TagService tagService;

    @Test
    @DisplayName(value = "Test GET /api/v1/posts/1 and return defined post")
    void testFindById() throws Exception {
        Post post = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .build();
        Optional<Post> optionalPost = Optional.of(post);
        when(postService.findById(1L))
                .thenReturn(optionalPost);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("news"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("bbc.com"));

        verify(postService, times(1)).findById(1L);
    }

    @Test
    @DisplayName(value = "Test GET /api/v1/posts/NOT_EXIST and return NOT FOUND")
    void testFindByIdWhichIsNotExist() throws Exception {
        Optional<Post> optionalPost = Optional.empty();
        when(postService.findById(100L))
                .thenReturn(optionalPost);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(postService, times(1)).findById(100L);
    }

    @Test
    @DisplayName(value = "Test DELETE /api/v1/posts/NOT_EXIST/star and return NOT FOUND")
    void testDeleteStarFromPostByIdWhichIsNotExist() throws Exception {
        Post post = null;
        when(postService.updatePostBySetStarFalse(100L))
                .thenReturn(post);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/posts/100/star")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(postService, times(1)).updatePostBySetStarFalse(100L);
    }

    @Test
    @DisplayName(value = "Test PUT /api/v1/posts/NOT_EXIST/star and return NOT FOUND")
    void testSetStarFromPostByIdWhichIsNotExist() throws Exception {
        Post post = null;
        when(postService.updatePostBySetStarTrue(100L))
                .thenReturn(post);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/posts/100/star")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(postService, times(1)).updatePostBySetStarTrue(100L);
    }

    @Test
    @DisplayName(value = "Test GET /api/v1/posts and return defined posts")
    void testFindAll() throws Exception {
        //prepare
        Post one = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .comments(new ArrayList<Comment>())
                .build();
        Post two = Post.builder()
                .id(2L)
                .title("sport")
                .comments(new ArrayList<Comment>())
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
    @DisplayName(value = "Test GET /api/v1/posts and return not found")
    void testFindAllButNotFound () throws Exception {
        List<Post> posts = new ArrayList<>();
        when(postService.getAll())
                .thenReturn(posts);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(postService, times(1)).getAll();
    }

    @Test
    @DisplayName(value = "Test GET /api/v1/posts/star and return not found")
    void testFindAllWithStarButNotFound () throws Exception {
        List<Post> posts = new ArrayList<>();
        when(postService.findByStarTrue())
                .thenReturn(posts);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/star")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(postService, times(1)).findByStarTrue();
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
                .comments(new ArrayList<Comment>())
                .build();
        Post two = Post.builder()
                .id(2L)
                .title("news")
                .comments(new ArrayList<Comment>())
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
                .comments(new ArrayList<Comment>())
                .build();
        Post two = Post.builder()
                .id(2L)
                .title("A")
                .content("football.com")
                .comments(new ArrayList<Comment>())
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
    @DisplayName(value = "Test GET /api/v1/posts/star")
    void getAllPostsWithStar() throws Exception {
        Post one = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .comments(new ArrayList<Comment>())
                .star(true)
                .build();
        Post two = Post.builder()
                .id(2L)
                .title("sport")
                .content("football.com")
                .comments(new ArrayList<Comment>())
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
    @DisplayName(value = "Test PUT /api/v1/posts/star: set star to post")
    void setValueForStarTrue() throws Exception {
        Post one = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .comments(new ArrayList<Comment>())
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
    @DisplayName(value = "Test DELETE /api/v1/posts/star: delete star from post")
    void updatePostBySetStarFalse() throws Exception {
        Post one = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .comments(new ArrayList<Comment>())
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

    @Test
    @DisplayName(value = "Test GET /api/v1/posts/tags/football, sport: get posts by tags")
    void getPostsByTags() throws Exception {
        Post one = Post.builder()
                .id(1L)
                .title("news")
                .content("bbc.com")
                .tags(new HashSet<Tag>())
                .star(true)
                .comments(new ArrayList<Comment>())
                .build();
        Post two = Post.builder()
                .id(2L)
                .title("sport")
                .content("football.com")
                .tags(new HashSet<Tag>())
                .star(true)
                .comments(new ArrayList<Comment>())
                .build();

        Tag sport = Tag.builder()
                .name("sport")
                .posts(new ArrayList<Post>())
                .build();
        Tag box = Tag.builder()
                .name("box")
                .posts(new ArrayList<Post>())
                .build();

        one.getTags().add(sport);
        two.getTags().add(box);
        sport.getPosts().add(one);
        sport.getPosts().add(two);

        List<String> tagsName = Arrays.asList(sport.getName(),box.getName());
        Set<Post> postSet = new HashSet<>();
        postSet.add(one);
        postSet.add(two);

        when(postService.getPostsByTags(tagsName)).thenReturn(postSet);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/tags/sport,box")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(hasSize(2)))

                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("news"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content").value("bbc.com"))

                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("sport"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("football.com"));

        verify(postService, times(1)).getPostsByTags(tagsName);
    }
}