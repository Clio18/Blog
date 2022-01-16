package com.luxcampus.Blog.repository.jdbc;
import com.luxcampus.Blog.entity.Post;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostRowMapper {
    public Post mapRow(ResultSet resultSet) throws SQLException {
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
