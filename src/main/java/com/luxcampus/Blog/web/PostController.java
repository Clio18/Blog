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

    @GetMapping("/all")
    public List<Post> findAll(){
        List<Post> posts = postService.getAll();
        logger.info("Posts {}", posts);
        return posts;
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
    public List<Post> findByTitle(@RequestParam String title){
        List<Post> posts = postService.findByTitleIs(title);
        logger.info("Get by title post {} ", posts);
        return posts;
    }

    @GetMapping("/sort")
    public List<Post> sortByTitle(){
        List<Post> posts = postService.findByOrderByTitleAsc();
        logger.info("Sort by title post {} ", posts);
        return posts;
    }
}
