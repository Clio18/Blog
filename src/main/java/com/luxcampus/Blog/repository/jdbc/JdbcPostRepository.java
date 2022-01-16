package com.luxcampus.Blog.repository.jdbc;
import com.luxcampus.Blog.entity.Post;
import com.luxcampus.Blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcPostRepository implements PostRepository {
    private static final PostRowMapper POST_ROW_MAPPER = new PostRowMapper();
    private static final String GET_ALL_SQL = "SELECT id, title, content FROM post;";
    private static final String GET_ID_BY_EMAIL_AND_PASSWORD_SQL = "SELECT id FROM post where title = ? and content = ?;";
    private static final String GET_BY_ID_SQL = "SELECT * FROM post where id = ?;";
    private static final String SAVE_SQL = "INSERT INTO post (title, content) VALUES (?, ?)";
    private static final String DELETE_SQL = "DELETE FROM post where id= ?";
    private static final String UPDATE_SQL = "UPDATE post SET title = ?, content = ? where id = ?";

    private final DataSource dataSource;

    @Override
    public List<Post> getAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<Post> posts = new ArrayList<>();
            while (resultSet.next()) {
                Post post = POST_ROW_MAPPER.mapRow(resultSet);
                posts.add(post);
            }
            return posts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean save(Post post) {
        boolean flag;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getContent());
            preparedStatement.executeUpdate();
            flag = true;
            return flag;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with post insert", e);
        }
    }

    @Override
    public boolean delete(int id) {
        boolean flag;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            if (result==0){
                throw new RuntimeException();
            }
            flag = true;
            return flag;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with post delete", e);
        }
    }

    //"UPDATE users SET email = ?, password = ? where id = ?";
    @Override
    public boolean update(Post post) {
        boolean flag;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getContent());
            int result = preparedStatement.executeUpdate();
            if (result==0){
                throw new RuntimeException();
            }
            flag = true;
            return flag;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with post update", e);
        }
    }

    @Override
    public Post getById(int id) {
        Post post = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                post = POST_ROW_MAPPER.mapRow(resultSet);
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Error with post retrieving", e);
        }
        return post;
    }

}
