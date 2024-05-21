package com.lvucko.cookshare.dao;

import com.lvucko.cookshare.models.Picture;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PictureDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Picture> getRecipePictures(long recipeId){
        String sql = """
                    SELECT * FROM pictures where pictures.recipeId = ?
                    """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Picture.class), recipeId);
    }

    public void addRecipeToPicture(long recipeId, String pathToPicture) {
        String sql = """
                    INSERT INTO pictures (recipeId, pathToPicture) VALUES (?,?)
                    """;
        jdbcTemplate.update(sql, recipeId, pathToPicture);
    }

    public void removeRecipeFromPictures(long recipeId){
        String sql = """
                    DELETE FROM pictures WHERE recipeId = ?
                    """;
        jdbcTemplate.update(sql, recipeId);
    }

}
