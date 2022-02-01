package com.luxcampus.Blog.service;

import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService implements PostServiceInterface {

    private final PostRepository postRepository;

    public List<Post> getAll() {
        List<Post> users = postRepository.findAll();
        System.out.println("Posts in blog: " + users.size());
        return users;
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public void update(Long id, Post post) {
        post.setId(id);
        postRepository.save(post);
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findByTitleIs(String title) {
        return postRepository.findByTitleIs(title);
    }

    @Override
    public List<Post> findByOrderByTitleAsc() {
        return postRepository.findByOrderByTitleAsc();
    }

    @Override
    public List<Post> findByStarTrue() {
        return postRepository.findByStarTrue();
    }

    public Post updatePostBySetStarTrue(Long id) {
        return postRepository.updatePostBySetStarTrue(id);
    }

    @Override
    public Post updatePostBySetStarFalse(Long id) {
        return postRepository.updatePostBySetStarFalse(id);
    }

}
