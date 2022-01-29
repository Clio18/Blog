package com.luxcampus.Blog.repository;

import com.luxcampus.Blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT id, created_on, text, post_id FROM comment WHERE post_id=?1",
            nativeQuery = true)
    List<Comment> findCommentsByPostId(Long postId);

    @Query(value = "SELECT id, created_on, text, post_id FROM comment WHERE post_id=?1 AND id=?2",
            nativeQuery = true)
    Comment findCommentByPostIdAndCommentId(Long postId, Long id);
}
