package com.luxcampus.Blog.service;

import com.luxcampus.Blog.entity.Comment;
import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.entity.Tag;
import com.luxcampus.Blog.repository.CommentRepository;
import com.luxcampus.Blog.repository.PostRepository;
import com.luxcampus.Blog.service.impl.CommentService;
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
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostService postService;

    @Test
    @DisplayName(value = "Test get all by post id and return defined list of comments")
    void getAllByPostId() {
        CommentService commentService = new CommentService(commentRepository, postService);
        Comment one = Comment.builder()
                .id(1L)
                .text("aaaa")
                .build();
        Comment two = Comment.builder()
                .id(2L)
                .text("bbbb")
                .build();

        List<Comment> list = List.of(one, two);

        when(commentRepository.findCommentsByPostId(1L)).thenReturn(list);

        List<Comment> actualComments = commentService.findCommentsByPostId(1L);

        assertEquals(list.size(), actualComments.size());
        assertEquals(list.get(1).getText(), actualComments.get(1).getText());
        assertEquals(list.get(1).getId(), actualComments.get(1).getId());

        verify(commentRepository, times(1)).findCommentsByPostId(1L);
    }

    @Test
    @DisplayName(value = "Test save and return checking method save in commentRepository calling")
    void save() {
        Post post = Post.builder()
                .id(1L)
                .title("sport")
                .content("football")
                .tags(new HashSet<Tag>())
                .comments(new ArrayList<Comment>())
                .star(false)
                .build();
        Optional<Post> optionalPost = Optional.of(post);

        when(postService.findById(1L)).thenReturn(optionalPost);

        CommentService commentService = new CommentService(commentRepository, postService);
        Comment one = Comment.builder()
                .id(1L)
                .text("aaaa")
                .build();

        when(commentRepository.save(one)).thenReturn(one);

        commentService.save(1L, one);
        verify(commentRepository, times(1)).save(one);
    }

    @Test
    @DisplayName(value = "Test find by id and return defined comment")
    void findById() {
        CommentService commentService = new CommentService(commentRepository, postService);
        Comment one = Comment.builder()
                .id(1L)
                .text("aaaa")
                .build();

        when(commentRepository.findCommentByPostIdAndCommentId(1L, 1L)).thenReturn(one);

        Comment actualComment = commentService.findCommentByPostIdAndCommentId(1L, 1L);
        assertEquals(one.getId(), actualComment.getId());
        assertEquals(one.getText(), actualComment.getText());

        verify(commentRepository, times(1)).findCommentByPostIdAndCommentId(1L, 1L);
    }
}