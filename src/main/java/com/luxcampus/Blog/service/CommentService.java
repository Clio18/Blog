package com.luxcampus.Blog.service;
import com.luxcampus.Blog.entity.Comment;
import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.repository.CommentRepository;
import com.luxcampus.Blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentServiceInterface {
    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    @Override
    public void save(Long postId, Comment comment) {

        Post post = postRepository.getById(postId);
        comment.setPost(post);
        comment.setCreated_on(LocalDateTime.now());
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> findCommentsByPostId(Long postId) {
        return commentRepository.findCommentsByPostId(postId);
    }

    @Override
    public Comment findCommentByPostIdAndCommentId(Long postId, Long id) {
        return commentRepository.findCommentByPostIdAndCommentId(postId, id);
    }
}
