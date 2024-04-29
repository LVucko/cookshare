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
    public List<String> getRecipePathToPictures(long recipeId){
        List<Picture> pictures = getRecipePictures(recipeId);
        List<String> pathToPictures = new ArrayList<>();
        for(Picture picture : pictures){
            pathToPictures.add(picture.getPathToPicture());
        }
        return pathToPictures;
    }

    public void addRecipeToPicture(long recipeId, String pathToPicture) {
        String sql = """
                    INSERT INTO pictures (recipeId, pathToPicture) VALUES (?,?)
                    """;
        jdbcTemplate.update(sql, recipeId, pathToPicture);
    }
}
