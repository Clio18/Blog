package com.luxcampus.Blog.service;

import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.entity.Tag;
import com.luxcampus.Blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Transactional
    public void delete(Long id) {
        Post post = postRepository.getById(id);
        Set<Tag> tags = post.getTags();
        if (!tags.isEmpty()){
            for (Tag tag : tags) {
                tag.getPosts().remove(post);
            }
        }
        postRepository.delete(post);
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

    @Override
    public Set<Post> getPostsByTags(List<String> tags) {
        return postRepository.findPostsByTags(tags);
    }

}
