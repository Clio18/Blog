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
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final PostServiceInterface postService;

    @GetMapping("/{id}/full")
    public PostWithCommentsDto findPostByIdWithComments(@PathVariable Long id) {
        Post post = postService.findById(id);
        PostWithCommentsDto postWithCommentsDto = getPostWithCommentsDto(post);
        logger.info("Get post {} ", postWithCommentsDto);
        return postWithCommentsDto;
    }

    @GetMapping("/{id}")
    public PostWithoutCommentsDto findPostById(@PathVariable Long id) {
        Post post = postService.findById(id);
        PostWithoutCommentsDto postWithoutCommentsDto = getPostWithOutCommentsDto(post);
        logger.info("Get post {} ", postWithoutCommentsDto);
        return postWithoutCommentsDto;
    }

    @GetMapping
    public List<PostWithCommentsDto> getAllPosts(@RequestParam(value = "title", required = false) String title,
                                                 @RequestParam(value = "sort", required = false) String sort) {
        logger.info("getAllPostsMethod");
        if (title != null) {
            logger.info("in findAllPostsByTitle method");
            List<Post> posts = postService.findByTitleIs(title);
            return getPostWithCommentsDtos(posts);
        } else if (sort != null) {
            logger.info("in findAllPostsSortedByTitle method");
            List<Post> posts = postService.findByOrderByTitleAsc();
            return getPostWithCommentsDtos(posts);
        } else {
            List<Post> posts = postService.getAll();
            return getPostWithCommentsDtos(posts);
        }
    }

    @GetMapping("/star")
    public List<PostWithCommentsDto> getAllPostsWithStar() {
        logger.info("getAllPostsMethod");
        logger.info("in findAllPostsByStar method");
        List<Post> posts = postService.findByStarTrue();
        return getPostWithCommentsDtos(posts);
    }

    @PutMapping("/{id}/star")
    public PostWithCommentsDto setValueForStarTrue(@PathVariable Long id) {
        Post post = postService.updatePostBySetStarTrue(id);
        return getPostWithCommentsDto(post);
    }

    @DeleteMapping("/{id}/star")
    public PostWithCommentsDto updatePostBySetStarFalse(@PathVariable Long id) {
        Post post = postService.updatePostBySetStarFalse(id);
        return getPostWithCommentsDto(post);
    }

    // void methods
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
    public void update(@PathVariable Long id, @RequestBody Post post) {
        postService.update(id, post);
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
        if(comments!=null){
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
