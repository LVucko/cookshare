package com.lvucko.cookshare.dao;

import com.lvucko.cookshare.dto.CommentCreationDto;
import com.lvucko.cookshare.dto.CommentDetailsDto;
import com.lvucko.cookshare.models.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentDao {
    private final JdbcTemplate jdbcTemplate;
    public List<CommentDetailsDto> getAllRecipeComments(long recipeId){
        String sql = """
                    select comments.*, users.username
                    from comments
                    left join users on comments.userid = users.id
                    where comments.recipeid = ?;
                    """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CommentDetailsDto.class), recipeId);
    }
    public List<Comment> getAllUserComments(long userId){
        String sql = """
                    SELECT * FROM comments where comments.userid = ?
                    """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Comment.class), userId);
    }
    public Long addNewComment(CommentCreationDto comment){
        String sql = """
                    INSERT INTO comments(userId, recipeId, time, comment)
                    VALUES (?, ?, CURRENT_TIMESTAMP, ?)
                    RETURNING id
                    """;
        return jdbcTemplate.queryForObject(sql, Long.class, comment.getUserId(), comment.getRecipeId(), comment.getComment());
    }
    public Comment getCommentById(long id){
        String sql = """
                SELECT * FROM comments WHERE comments.id = ?
                """;
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Comment.class), id);
    }
    public void deleteComment(long commentId){
        String sql = """
                    DELETE FROM comments WHERE comments.id = ?
                    """;
        jdbcTemplate.update(sql, commentId);
    }
    public void deleteAllCommentsFromRecipe(long recipeId){
        String sql = """
                    DELETE FROM comments WHERE comments.recipeid = ?
                    """;
        jdbcTemplate.update(sql, recipeId);
    }
    public void deleteAllCommentsFromUser(long userId){
        String sql = """
                    DELETE FROM comments WHERE comments.userid = ?
                    """;
        jdbcTemplate.update(sql, userId);
    }
}
