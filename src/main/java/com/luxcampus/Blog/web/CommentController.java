package com.luxcampus.Blog.web;

import com.luxcampus.Blog.entity.Comment;
import com.luxcampus.Blog.entity.dto.CommentWithoutPostDto;
import com.luxcampus.Blog.service.CommentServiceInterface;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class CommentController {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final CommentServiceInterface commentService;

    @GetMapping("/{id}/comments")
    public ResponseEntity<Object> findCommentsByPostId(@PathVariable Long id) {
        logger.info("Get comments");
        List<Comment> comments = commentService.findCommentsByPostId(id);
        if (comments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("The post was not found \n");
        } else {
            List<CommentWithoutPostDto> commentWithoutPostDtoList = getCommentWithoutPostDtos(comments);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(commentWithoutPostDtoList);
        }
    }

    @GetMapping("/{postId}/comments/{id}")
    public ResponseEntity<Object> findCommentByPostIdAndCommentId(@PathVariable Long postId, @PathVariable Long id) {
        logger.info("Get comment");
        Comment comment = commentService.findCommentByPostIdAndCommentId(postId, id);
        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("The comment was not found by provided post and comment ids (CODE 404)\n");
        } else {
            CommentWithoutPostDto commentWithoutPostDto = getCommentWithoutPostDto(comment);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(commentWithoutPostDto);
        }
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<Object> findCommentByCommentId(@PathVariable Long id) {
        logger.info("Get comment V2");
        Optional<Comment> optionalComment = commentService.findById(id);
        if (optionalComment.isPresent()) {
            CommentWithoutPostDto commentWithoutPostDto = getCommentWithoutPostDto(optionalComment.get());
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(commentWithoutPostDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("The comment was not found by provided comment id (CODE 404)\n");
        }
    }


    @PostMapping("/{postId}/comments")
    public void addCommentToPostById(@PathVariable Long postId,
                                     @RequestBody Comment comment) {
        logger.info("Save comment");
        commentService.save(postId, comment);

    }

    private CommentWithoutPostDto getCommentWithoutPostDto(Comment comment) {
        CommentWithoutPostDto commentWithoutPostDto = CommentWithoutPostDto.builder()
                .id(comment.getId())
                .created_on(comment.getCreated_on())
                .text(comment.getText())
                .build();
        return commentWithoutPostDto;
    }

    private List<CommentWithoutPostDto> getCommentWithoutPostDtos(List<Comment> comments) {
        List<CommentWithoutPostDto> commentWithoutPostDtos = new ArrayList<>();
        if (comments != null) {
            for (Comment comment : comments) {
                CommentWithoutPostDto commentWithoutPostDto = CommentWithoutPostDto.builder()
                        .id(comment.getId())
                        .created_on(comment.getCreated_on())
                        .text(comment.getText())
                        .build();
                commentWithoutPostDtos.add(commentWithoutPostDto);
            }
        }
        return commentWithoutPostDtos;
    }

}
