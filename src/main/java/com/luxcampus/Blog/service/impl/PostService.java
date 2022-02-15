package com.luxcampus.Blog.service.impl;

import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.entity.Tag;
import com.luxcampus.Blog.entity.dto.PostWithCommentsAndTagsDto;
import com.luxcampus.Blog.entity.dto.PostWithCommentsDto;
import com.luxcampus.Blog.entity.dto.PostWithoutCommentsDto;
import com.luxcampus.Blog.repository.PostRepository;
import com.luxcampus.Blog.service.PostServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.luxcampus.Blog.util.UtilService.*;

@Service
@RequiredArgsConstructor
public class PostService implements PostServiceInterface {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    @Override
    public List<PostWithCommentsDto> getAllPostsWithComments() {
        List<Post> posts = postRepository.findAll();
        System.out.println("Posts in blog: " + posts.size());
        List<PostWithCommentsDto> postWithCommentsDtos = getPostsWithCommentsDtos(posts);
        return postWithCommentsDtos;
    }

    @Transactional(readOnly = true)
    @Override
    public PostWithCommentsDto findPostByIdWithComments(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        Post post = optionalPost.get();
        PostWithCommentsDto postWithCommentsDto = getPostWithCommentsDto(post);
        return postWithCommentsDto;
    }

    @Transactional(readOnly = true)
    @Override
    public PostWithCommentsAndTagsDto findPostByIdWithCommentsAddTags(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        Post post = optionalPost.get();
        PostWithCommentsAndTagsDto postWithCommentsAndTagsDto = getPostWithCommentsAndTagsDto(post);
        return postWithCommentsAndTagsDto;
    }

    @Transactional(readOnly = true)
    @Override
    public PostWithoutCommentsDto findPostByIdWithoutComments(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        Post post = optionalPost.get();
        PostWithoutCommentsDto postWithoutCommentsDto = getPostWithOutCommentsDto(post);
        return postWithoutCommentsDto;
    }

    @Transactional
    @Override
    public List<PostWithCommentsDto> findByTitleIs(String title) {
        List<Post> posts = postRepository.findByTitleIs(title);
        List<PostWithCommentsDto> postWithCommentsDtoList =  getPostsWithCommentsDtos(posts);
        return postWithCommentsDtoList;
    }

    @Transactional
    @Override
    public List<PostWithCommentsDto> findByOrderByTitleAsc() {
        List<Post> posts = postRepository.findByOrderByTitleAsc();
        List<PostWithCommentsDto> postWithCommentsDtoList =  getPostsWithCommentsDtos(posts);
        return postWithCommentsDtoList;
    }

    @Transactional
    @Override
    public List<PostWithCommentsDto> findByStarTrue() {
        List<Post> posts = postRepository.findByStarTrue();
        List<PostWithCommentsDto> postsWithCommentsDtos = getPostsWithCommentsDtos(posts);
        return postsWithCommentsDtos;
    }

    @Transactional
    @Override
    public PostWithCommentsDto updatePostBySetStarTrue(Long id) {
        Post post = postRepository.updatePostBySetStarTrue(id);
        PostWithCommentsDto postWithCommentsDto = getPostWithCommentsDto(post);
        return postWithCommentsDto;
    }

    @Transactional
    @Override
    public PostWithCommentsDto updatePostBySetStarFalse(Long id) {
        Post post = postRepository.updatePostBySetStarFalse(id);
        PostWithCommentsDto postWithCommentsDto = getPostWithCommentsDto(post);
        return postWithCommentsDto;
    }

    @Transactional
    @Override
    public List<PostWithCommentsAndTagsDto> getPostsByTags(List<String> tags) {
        Set<Post> posts = postRepository.findPostsByTags(tags);
        List<PostWithCommentsAndTagsDto> postWithCommentsAndTagsDtos = new ArrayList<>(posts.size());
        for (Post post : posts) {
            postWithCommentsAndTagsDtos.add(getPostWithCommentsAndTagsDto(post));
        }
        return postWithCommentsAndTagsDtos;
    }

    @Transactional
    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Post post = postRepository.getById(id);
        Set<Tag> tags = post.getTags();
        if (!tags.isEmpty()) {
            for (Tag tag : tags) {
                tag.getPosts().remove(post);
            }
        }
        postRepository.delete(post);
    }

    @Transactional
    @Override
    public void update(Long id, Post post) {
        post.setId(id);
        postRepository.save(post);
    }

    @Transactional
    @Override
    public Optional<Post> findById(Long id){
        return postRepository.findById(id);
    }

    @Transactional
    @Override
    public List<Post> findAll(){
        return postRepository.findAll();
    }
}
