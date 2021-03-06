package com.luxcampus.Blog.repository;
import com.luxcampus.Blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;


@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findByTitleIs(String title);

    List<Post> findByOrderByTitleAsc();

    List<Post> findByStarTrue();

    @Query(value = "UPDATE Post SET star=true WHERE id=?1 RETURNING *",
            nativeQuery = true)
    Post updatePostBySetStarTrue(Long id);

    @Query(value = "UPDATE Post SET star=false WHERE id=?1 RETURNING *",
            nativeQuery = true)
    Post updatePostBySetStarFalse(Long id);

    @Query("select p from Post p left join p.tags tags where tags.name in ?1")
    Set<Post> findPostsByTags(List<String> tags);
}
