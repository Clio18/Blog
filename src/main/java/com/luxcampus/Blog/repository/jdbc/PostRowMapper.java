package com.luxcampus.Blog.repository.jdbc;
import com.luxcampus.Blog.entity.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostRowMapper implements RowMapper<Post> {
    public Post mapRow(ResultSet resultSet, int numRow) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title").trim();
        String content = resultSet.getString("content").trim();

        Post post = Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();

        return post;
    }
}
