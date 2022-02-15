package com.luxcampus.Blog.web;

import com.luxcampus.Blog.entity.Comment;
import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.entity.Tag;
import com.luxcampus.Blog.entity.dto.*;
import com.luxcampus.Blog.service.PostServiceInterface;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final PostServiceInterface postService;

    @GetMapping("/{id}/comments")
    public ResponseEntity<Object> findPostByIdWithComments(@PathVariable Long id) {
        PostWithCommentsDto postWithCommentsDto = postService.findPostByIdWithComments(id);
        logger.info("Get full post {} ", postWithCommentsDto);
        return ResponseEntity.ok(postWithCommentsDto);
    }

    @GetMapping("/{id}/full")
    public ResponseEntity<Object> findPostByIdWithCommentsAndTags(@PathVariable Long id) {
        PostWithCommentsAndTagsDto postWithCommentsAndTagsDto = postService.findPostByIdWithCommentsAddTags(id);
        logger.info("Get full post {} ", postWithCommentsAndTagsDto);
        return ResponseEntity.ok(postWithCommentsAndTagsDto);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> findPostByIdWithoutComments(@PathVariable Long id) {
        PostWithoutCommentsDto postWithoutCommentsDto = postService.findPostByIdWithoutComments(id);
        logger.info("Get post {} ", postWithoutCommentsDto);
        return ResponseEntity.ok(postWithoutCommentsDto);
    }


    @GetMapping
    public ResponseEntity<Object> getAllPosts(@RequestParam(value = "title", required = false) String title,
                                              @RequestParam(value = "sort", required = false) String sort) {
        logger.info("getAllPostsMethod");
        if (title != null) {
            logger.info("in findAllPostsByTitle method");
            List<PostWithCommentsDto> postsWithCommentsDtos = postService.findByTitleIs(title);
            return ResponseEntity.ok(postsWithCommentsDtos);
        } else if (sort != null) {
            logger.info("in findAllPostsSortedByTitle method");
            List<PostWithCommentsDto> postsWithCommentsDtos = postService.findByOrderByTitleAsc();
            return ResponseEntity.ok(postsWithCommentsDtos);
        } else {
            List<PostWithCommentsDto> postWithCommentsDto = postService.getAllPostsWithComments();
            return ResponseEntity.ok(postWithCommentsDto);
        }
    }

    @GetMapping("/star")
    public ResponseEntity<Object> getAllPostsWithStar() {
        logger.info("in findAllPostsByStar method");
        List<PostWithCommentsDto> postsWithCommentsDtos = postService.findByStarTrue();
        return ResponseEntity.ok(postsWithCommentsDtos);
    }

    @PutMapping("/{id}/star")
    public ResponseEntity<Object> setValueForStarTrue(@PathVariable Long id) {
        PostWithCommentsDto postWithCommentsDto = postService.updatePostBySetStarTrue(id);
        logger.info("Post with comment {} - setting star", postWithCommentsDto);
        return ResponseEntity.ok(postWithCommentsDto);
    }

    @DeleteMapping("/{id}/star")
    public ResponseEntity<Object> updatePostBySetStarFalse(@PathVariable Long id) {
        PostWithCommentsDto postWithCommentsDto = postService.updatePostBySetStarFalse(id);
        logger.info("Post with comment {} - removing star", postWithCommentsDto);
        return ResponseEntity.ok(postWithCommentsDto);
    }

    @GetMapping("/tags/{tags}")
    public ResponseEntity<List<PostWithCommentsAndTagsDto>> getPostsByTags(@PathVariable List<String> tags) {
        List<PostWithCommentsAndTagsDto> postWithCommentsAndTagsDtos = postService.getPostsByTags(tags);
        return ResponseEntity.status(HttpStatus.OK).body(postWithCommentsAndTagsDtos);
    }

    @PostMapping
    public ResponseEntity<Post> save(@RequestBody Post post) {
        postService.save(post);
        logger.info("Add post {} ", post);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        postService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Post post) {
        if (post.getTitle() == null || post.getContent() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body("The Post is missing: please provide data for update (CODE 400)\n");
        }
        postService.update(id, post);
        return ResponseEntity.status(HttpStatus.OK).body("Post is updated \n");
    }
}
