package com.lvucko.cookshare.dao;

import com.lvucko.cookshare.models.Favourite;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FavouriteDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Favourite> getUserFavourites(Long userId){
        String sql = """
                    SELECT * from favourites WHERE userId = ?;
                    """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Favourite.class), userId);
    }
    public Boolean isFavourite(Long userId, Long recipeId){
        String sql = """
                    SELECT EXISTS(SELECT * from favourites WHERE userId = ? AND recipeId = ?)
                    """;
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, userId, recipeId));
    }
    public void removeFromFavourites(Long userId, Long recipeId){
        String sql = """
                    DELETE FROM favourites WHERE userId = ? AND recipeId = ?
                    """;
        jdbcTemplate.update(sql, userId, recipeId);
    }
    public void addToFavourites(Long userId, Long recipeId){
        String sql = """
                INSERT INTO favourites (userId, recipeId) VALUES (?, ?)
                """;
        jdbcTemplate.update(sql, userId, recipeId);
    }
    public void removeAllUserFavourites(Long userId){
        String sql = """
                    DELETE FROM favourites WHERE userID = ?
                    """;
        jdbcTemplate.update(sql, userId);
    }
    public void removeAllRecipeFavourites(Long recipeId){
        String sql = """
                    DELETE FROM favourites WHERE recipeId = ?
                    """;
        jdbcTemplate.update(sql, recipeId);
    }
    public Long getNumberOfFavourites(Long recipeId){
        String sql = """
                    SELECT COUNT (recipeId) FROM favourites WHERE recipeId = ?
                    """;
        return jdbcTemplate.queryForObject(sql, Long.class, recipeId);
    }
}
