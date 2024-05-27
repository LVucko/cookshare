package com.lvucko.cookshare.dao;

import com.lvucko.cookshare.dto.RatingCreationDto;
import com.lvucko.cookshare.models.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RatingDao {
    private final JdbcTemplate jdbcTemplate;
    public Rating getRating(long ratingId){
        String sql = """
                    SELECT * FROM ratings WHERE ratings.id = ?
                    """;
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Rating.class), ratingId);
    }
    public List<Rating> getAllRecipeRatings(long recipeId){
        String sql = """
                    SELECT * FROM ratings WHERE ratings.recipeId = ?
                    """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Rating.class), recipeId);
    }
    public void deleteAllRecipeRatings(long recipeId){
        String sql = """
                    DELETE FROM ratings WHERE ratings.recipeId = ?
                    """;
        jdbcTemplate.update(sql, recipeId);
    }
    public void deleteAllUserRatings(long userId){
        String sql = """
                    DELETE * FROM ratings WHERE ratings.userId = ?
                    """;
        jdbcTemplate.update(sql, userId);
    }
    public List<Rating> getAllUserRatings(long userId){
        String sql = """
                    SELECT * FROM ratings WHERE ratings.userId = ?
                    """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Rating.class), userId);
    }
    public Long getUserRatingForRecipe(long userId, long recipeId){
        String sql = """
                    SELECT rating FROM ratings WHERE ratings.userId = ? AND ratings.recipeId = ?
                    """;
        return jdbcTemplate.queryForObject(sql, Long.class, userId, recipeId);
    }
    public Double getAverageRecipeRating(long recipeId){

        String sql = """
                    SELECT AVG(rating) FROM ratings WHERE ratings.recipeId = ?
                    """;
        double averageRating;
        try {
            averageRating =  jdbcTemplate.queryForObject(sql, Double.class, recipeId);
        }
        catch(Exception e){
            return -1D;
        }
        return averageRating;


    }
    public Long addNewRating(RatingCreationDto rating){
        String sql = """
                    INSERT INTO ratings(userId, recipeId, rating)
                    VALUES (?, ?, ?)
                    RETURNING id
                    """;
        return jdbcTemplate.queryForObject(sql, Long.class, rating.getUserId(), rating.getRecipeId(), rating.getRating());
    }

}
