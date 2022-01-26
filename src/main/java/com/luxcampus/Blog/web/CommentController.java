package com.luxcampus.Blog.web;
import com.luxcampus.Blog.entity.Comment;
import com.luxcampus.Blog.entity.dto.CommentWithoutPostDto;
import com.luxcampus.Blog.service.CommentServiceInterface;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class CommentController {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final CommentServiceInterface commentService;

    @GetMapping("/{id}/comments")
    public List<CommentWithoutPostDto> findCommentsByPostId(@PathVariable Long id) {
        logger.info("Get comments");
        List<Comment> comments = commentService.findCommentsByPostId(id);
        return getCommentWithoutPostDtos(comments);
    }

    @GetMapping("/{postId}/comments/{id}")
    public CommentWithoutPostDto findCommentByPostIdAndCommentId(@PathVariable Long postId, @PathVariable Long id) {
        logger.info("Get comment");
        Comment comment = commentService.findCommentByPostIdAndCommentId(postId, id);
        return getCommentWithoutPostDto(comment);
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
