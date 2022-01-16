package com.luxcampus.Blog.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Post {
    private int id;
    private String title;
    private String content;
}
