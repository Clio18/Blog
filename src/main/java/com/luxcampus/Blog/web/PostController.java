package com.luxcampus.Blog.web;

import com.luxcampus.Blog.entity.Comment;
import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.entity.dto.CommentWithoutPostDto;
import com.luxcampus.Blog.entity.dto.PostWithCommentsDto;
import com.luxcampus.Blog.entity.dto.PostWithoutCommentsDto;
import com.luxcampus.Blog.service.PostServiceInterface;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final PostServiceInterface postService;

    @GetMapping("/{id}/full")
    public ResponseEntity<Object> findPostByIdWithComments(@PathVariable Long id) {
        Optional<Post> optionalPost = postService.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            PostWithCommentsDto postWithCommentsDto = getPostWithCommentsDto(post);
            logger.info("Get full post {} ", postWithCommentsDto);
            return ResponseEntity.ok(postWithCommentsDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("The Post is not found by passing id (CODE 404)\n");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findPostById(@PathVariable Long id) {
        Optional<Post> optionalPost = postService.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            PostWithoutCommentsDto postWithoutCommentsDto = getPostWithOutCommentsDto(post);
            logger.info("Get post {} ", postWithoutCommentsDto);
            return ResponseEntity.ok(postWithoutCommentsDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("The Post is not found by passing id (CODE 404)\n");
        }

    }

    @GetMapping
    public ResponseEntity<Object> getAllPosts(@RequestParam(value = "title", required = false) String title,
                                              @RequestParam(value = "sort", required = false) String sort) {
        logger.info("getAllPostsMethod");
        if (title != null) {
            logger.info("in findAllPostsByTitle method");
            List<Post> posts = postService.findByTitleIs(title);
            if (posts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("The Posts are not found by passing title (CODE 404)\n");
            } else {
                List<PostWithCommentsDto> postWithCommentsDtos = getPostWithCommentsDtos(posts);
                return ResponseEntity.ok(postWithCommentsDtos);
            }
        } else if (sort != null) {
            logger.info("in findAllPostsSortedByTitle method");
            List<Post> posts = postService.findByOrderByTitleAsc();
            if (posts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("The Posts are not found when sorting (CODE 404)\n");
            } else {
                List<PostWithCommentsDto> postWithCommentsDtos = getPostWithCommentsDtos(posts);
                return ResponseEntity.ok(postWithCommentsDtos);
            }
        } else {
            List<Post> posts = postService.getAll();
            if (posts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("The Posts are not found (CODE 404)\n");
            } else {
                List<PostWithCommentsDto> postWithCommentsDtos = getPostWithCommentsDtos(posts);
                return ResponseEntity.ok(postWithCommentsDtos);
            }
        }
    }

    @GetMapping("/star")
    public ResponseEntity<Object> getAllPostsWithStar() {
        logger.info("in findAllPostsByStar method");
        List<Post> posts = postService.findByStarTrue();
        if (posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("The Posts with star are not found (CODE 404)\n");
        } else {
            List<PostWithCommentsDto> postWithCommentsDtos = getPostWithCommentsDtos(posts);
            return ResponseEntity.ok(postWithCommentsDtos);
        }
    }

    @PutMapping("/{id}/star")
    public ResponseEntity<Object> setValueForStarTrue(@PathVariable Long id) {
        Post post = postService.updatePostBySetStarTrue(id);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("The Post without star was not found (CODE 404)\n");
        } else {
            PostWithCommentsDto postWithCommentsDto = getPostWithCommentsDto(post);
            logger.info("Post with comment {} - setting star", postWithCommentsDto);
            return ResponseEntity.ok(postWithCommentsDto);
        }
    }

    @DeleteMapping("/{id}/star")
    public ResponseEntity<Object> updatePostBySetStarFalse(@PathVariable Long id) {
        Post post = postService.updatePostBySetStarFalse(id);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("The Post with star was not found (CODE 404)\n");
        } else {
            PostWithCommentsDto postWithCommentsDto = getPostWithCommentsDto(post);
            logger.info("Post with comment {} - removing star", postWithCommentsDto);
            return ResponseEntity.ok(postWithCommentsDto);
        }
    }

    @PostMapping
    public void save(@RequestBody Post post) {
        postService.save(post);
        logger.info("Add post {} ", post);
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

    //dto conversion methods
    private List<PostWithCommentsDto> getPostWithCommentsDtos(List<Post> posts) {
        List<PostWithCommentsDto> postWithCommentsDtos = new ArrayList<>();
        for (Post post : posts) {
            List<Comment> comments = post.getComments();
            List<CommentWithoutPostDto> commentWithoutPostDtos = new ArrayList<>();

            if (comments != null) {
                for (Comment comment : comments) {
                    CommentWithoutPostDto commentWithoutPostDto = CommentWithoutPostDto.builder()
                            .created_on(comment.getCreated_on())
                            .id(comment.getId())
                            .text(comment.getText())
                            .build();
                    commentWithoutPostDtos.add(commentWithoutPostDto);
                }
            }

            PostWithCommentsDto postWithCommentsDto = PostWithCommentsDto.builder()
                    .id(post.getId())
                    .content(post.getContent())
                    .star(post.isStar())
                    .title(post.getTitle())
                    .comments(commentWithoutPostDtos)
                    .build();

            postWithCommentsDtos.add(postWithCommentsDto);
        }
        return postWithCommentsDtos;
    }

    private PostWithCommentsDto getPostWithCommentsDto(Post post) {
        List<Comment> comments = post.getComments();
        List<CommentWithoutPostDto> commentWithoutPostDtos = new ArrayList<>();
        if (comments != null) {
            for (Comment comment : comments) {
                CommentWithoutPostDto commentWithoutPostDto = CommentWithoutPostDto.builder()
                        .text(comment.getText())
                        .id(comment.getId())
                        .created_on(comment.getCreated_on())
                        .build();
                commentWithoutPostDtos.add(commentWithoutPostDto);
            }
        }
        PostWithCommentsDto postWithCommentsDto = PostWithCommentsDto.builder()
                .title(post.getTitle())
                .id(post.getId())
                .star(post.isStar())
                .content(post.getContent())
                .comments(commentWithoutPostDtos)
                .build();

        return postWithCommentsDto;
    }

    private PostWithoutCommentsDto getPostWithOutCommentsDto(Post post) {
        PostWithoutCommentsDto postWithoutCommentsDto = PostWithoutCommentsDto.builder()
                .title(post.getTitle())
                .id(post.getId())
                .star(post.isStar())
                .content(post.getContent())
                .build();
        return postWithoutCommentsDto;
    }
}
