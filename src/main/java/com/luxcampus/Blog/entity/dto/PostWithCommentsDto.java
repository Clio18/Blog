package com.luxcampus.Blog.entity.dto;

import com.luxcampus.Blog.entity.Comment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostWithCommentsDto {
    private Long id;
    private String title;
    private String content;
    private boolean star;
    private List<CommentWithoutPostDto> comments;
}
