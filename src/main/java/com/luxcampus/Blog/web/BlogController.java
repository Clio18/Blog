package com.luxcampus.Blog.web;
import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.service.PostServiceInterface;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
        logger.info("posts {}", posts);
        return posts;
    }

    @PostMapping
    public void save(@RequestBody Post post){
        postService.save(post);
        logger.info("add post {}: ", post);
    }

    @PostMapping
    public Post findById(@PathVariable int id){
        Post post = postService.findById(id);
        return post;
    }
}
