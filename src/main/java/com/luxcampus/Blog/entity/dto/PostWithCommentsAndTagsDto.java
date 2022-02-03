package com.luxcampus.Blog.entity.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class PostWithCommentsAndTagsDto {
    private Long id;
    private String title;
    private String content;
    private boolean star;
    private List<CommentWithoutPostDto> comments;
    private Set<TagWithoutPostDto> tags;
}
