package com.luxcampus.Blog.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagWithoutPostDto {
    private Long id;
    private String name;
}
