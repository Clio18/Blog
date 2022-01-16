package com.luxcampus.Blog.repository.jdbc;

import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcPostRepository implements PostRepository {
    private static final PostRowMapper POST_ROW_MAPPER = new PostRowMapper();
    private static final String GET_ALL_SQL = "SELECT id, title, content FROM post;";
    private static final String SAVE_SQL = "INSERT INTO post (title, content) VALUES (:title, :content)";
    private static final String GET_BY_ID_SQL = "SELECT * FROM post where id = ?;";

    private static final String GET_ID_BY_EMAIL_AND_PASSWORD_SQL = "SELECT id FROM post where title = ? and content = ?;";
    private static final String DELETE_SQL = "DELETE FROM post where id= ?";
    private static final String UPDATE_SQL = "UPDATE post SET title = ?, content = ? where id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate nameParameterJdbcTemplate;

    @Override
    public List<Post> getAll() {
        return jdbcTemplate.query(GET_ALL_SQL, POST_ROW_MAPPER);
    }

    @Override
    public void save(Post post) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", post.getTitle());
        parameters.put("content", post.getContent());
        nameParameterJdbcTemplate.update(SAVE_SQL, parameters);
    }

    @Override
    public Post getById(int id) {
        Post post = jdbcTemplate.queryForObject(GET_BY_ID_SQL, new Object[]{id}, POST_ROW_MAPPER);
        return post;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Post post) {

    }
}
