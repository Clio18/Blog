package com.luxcampus.Blog.util;

import com.luxcampus.Blog.entity.Comment;
import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.entity.Tag;
import com.luxcampus.Blog.entity.dto.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UtilService {

    public static List<PostWithCommentsDto> getPostsWithCommentsDtos(List<Post> posts) {
        List<PostWithCommentsDto> postWithCommentsDtos = new ArrayList<>(posts.size());
        for (Post post : posts) {
            List<Comment> comments = post.getComments();
            List<CommentWithoutPostDto> commentWithoutPostDtos = new ArrayList<>(comments.size());

            for (Comment comment : comments) {
                CommentWithoutPostDto commentWithoutPostDto = CommentWithoutPostDto.builder()
                        .created_on(comment.getCreated_on())
                        .id(comment.getId())
                        .text(comment.getText())
                        .build();
                commentWithoutPostDtos.add(commentWithoutPostDto);
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

    public static PostWithCommentsDto getPostWithCommentsDto(Post post) {
        List<Comment> comments = post.getComments();
        List<CommentWithoutPostDto> commentWithoutPostDtos = new ArrayList<>(comments.size());
        for (Comment comment : comments) {
            CommentWithoutPostDto commentWithoutPostDto = CommentWithoutPostDto.builder()
                    .text(comment.getText())
                    .id(comment.getId())
                    .created_on(comment.getCreated_on())
                    .build();
            commentWithoutPostDtos.add(commentWithoutPostDto);
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

    public static PostWithoutCommentsDto getPostWithOutCommentsDto(Post post) {
        PostWithoutCommentsDto postWithoutCommentsDto = PostWithoutCommentsDto.builder()
                .title(post.getTitle())
                .id(post.getId())
                .star(post.isStar())
                .content(post.getContent())
                .build();
        return postWithoutCommentsDto;
    }

    public static PostWithCommentsAndTagsDto getPostWithCommentsAndTagsDto(Post post) {
        List<Comment> comments = post.getComments();
        List<CommentWithoutPostDto> commentWithoutPostDtos = new ArrayList<>(comments.size());
        for (Comment comment : comments) {
            CommentWithoutPostDto commentWithoutPostDto = CommentWithoutPostDto.builder()
                    .text(comment.getText())
                    .id(comment.getId())
                    .created_on(comment.getCreated_on())
                    .build();
            commentWithoutPostDtos.add(commentWithoutPostDto);
        }
        Set<Tag> tags = post.getTags();
        Set<TagWithoutPostDto> tagWithoutPostDtos = new HashSet<>();
        if (tags != null) {
            for (Tag tag : tags) {
                TagWithoutPostDto tagWithoutPostDto = TagWithoutPostDto.builder()
                        .id(tag.getId())
                        .name(tag.getName())
                        .build();
                tagWithoutPostDtos.add(tagWithoutPostDto);
            }
        }


        PostWithCommentsAndTagsDto postWithCommentsAndTagDto = PostWithCommentsAndTagsDto.builder()
                .title(post.getTitle())
                .id(post.getId())
                .star(post.isStar())
                .content(post.getContent())
                .comments(commentWithoutPostDtos)
                .tags(tagWithoutPostDtos)
                .build();

        return postWithCommentsAndTagDto;
    }

    public static List<PostWithCommentsAndTagsDto> getPostsWithCommentsAndTagsDto(List<Post> posts) {
        List<PostWithCommentsAndTagsDto> list = new ArrayList<>(posts.size());
        for (Post post : posts) {
            list.add(getPostWithCommentsAndTagsDto(post));
        }
        return list;
    }
}
