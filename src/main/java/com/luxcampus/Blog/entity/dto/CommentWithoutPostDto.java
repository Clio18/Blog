package com.luxcampus.Blog.entity.dto;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class CommentWithoutPostDto {
    private Long id;
    private String text;
    private LocalDateTime created_on;
}
