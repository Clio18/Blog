package com.luxcampus.Blog.web;
import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.service.PostServiceInterface;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/blog")
@RequiredArgsConstructor
//@Slf4j
public class BlogController {

    Logger logger = LoggerFactory.getLogger(getClass());

    private final PostServiceInterface postService;


    @GetMapping
    public List<Post> findAll(){
        List<Post> posts = postService.getAll();
        logger.info("post {}", posts);
        return posts;
    }
}
