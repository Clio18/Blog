package com.luxcampus.Blog.entity.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostWithoutCommentsDto {
    private Long id;
    private String title;
    private String content;
    private boolean star;
}
