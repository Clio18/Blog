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
        List<Post> users = postRepository.getAll();
        System.out.println("Posts in blog: " + users.size());
        return users;
    }

    public void save(Post post){
       postRepository.save(post);
    }

    public void delete(int id){
        postRepository.delete(id);
    }

    public void update(int id, Post post){
        postRepository.update(id, post);
    }

    @Override
    public Post findById(int id) {
        return postRepository.getById(id);
    }

}
