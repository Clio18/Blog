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

    public boolean save(Post post){
        return postRepository.save(post);
    }

    public boolean delete(int id){
        return postRepository.delete(id);
    }

    public boolean update(Post post){
        return postRepository.update(post);
    }

}
