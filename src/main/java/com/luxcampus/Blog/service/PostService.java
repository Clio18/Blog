package com.luxcampus.Blog.service;
import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements PostServiceInterface {
    private final PostRepository postRepository;

    public List<Post> getAll() {
        List<Post> users = postRepository.findAll();
        System.out.println("Posts in blog: " + users.size());
        return users;
    }

    public void save(Post post){
       postRepository.save(post);
    }

    public void delete(Long id){
        Post post = postRepository.getById(id);
        postRepository.delete(post);
    }

    public void update(Long id, Post post){
        post.setId(id);
        postRepository.save(post);
    }

    @Override
    public Post findById(Long id) {
        return postRepository.getById(id);
    }

}
