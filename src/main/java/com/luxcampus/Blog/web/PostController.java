package com.luxcampus.Blog.web;
import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.service.PostServiceInterface;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final PostServiceInterface postService;


    @GetMapping ("/{id}")
    public Post findById(@PathVariable Long id){
        Post post = postService.findById(id);
        logger.info("Get post {} ", post);
        return post;
    }

    @PostMapping
    public void save(@RequestBody Post post){
        postService.save(post);
        logger.info("Add post {} ", post);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        postService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Post post){
        postService.update(id, post);
    }

    @GetMapping
    public List<Post> getAllPosts(@RequestParam(value = "title", required = false) String title,
                                  @RequestParam(value = "sort", required = false) String sort) {
        logger.info("getAllPostsMethod");
        if (title != null) {
            logger.info("in findAllPostsByTitle method");
            return postService.findByTitleIs(title);
        } else if (sort != null) {
            logger.info("in findAllPostsSortedByTitle method");
            return postService.findByOrderByTitleAsc();
        } else {
            return postService.getAll();
        }
    }


}
