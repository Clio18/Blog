package com.luxcampus.Blog.service;

import com.luxcampus.Blog.entity.Comment;
import com.luxcampus.Blog.repository.CommentRepository;
import com.luxcampus.Blog.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;

    @Test
    void getAllByPostId() {
        CommentService commentService = new CommentService(commentRepository, postRepository);
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
    void save() {
        CommentService commentService = new CommentService(commentRepository, postRepository);
        Comment one = Comment.builder()
                .id(1L)
                .text("aaaa")
                .build();

        when(commentRepository.save(one)).thenReturn(one);

        commentService.save(any(Long.class), one);
        verify(commentRepository, times(1)).save(one);
    }

    @Test
    void findById() {
        CommentService commentService = new CommentService(commentRepository, postRepository);
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