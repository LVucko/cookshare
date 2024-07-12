package com.lvucko.cookshare.dao;

import com.lvucko.cookshare.models.Picture;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PictureDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Picture> getRecipePictures(long recipeId){
        String sql = """
                    SELECT pictures.* from pictures
                    INNER JOIN recipepictures
                    ON  pictures.id=recipepictures.pictureId
                    WHERE recipepictures.recipeid = ?
                    """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Picture.class), recipeId);
    }
    public Picture getPicture(long pictureId){
        String sql = """
                    SELECT * from pictures where pictures.id = ?
                    """;
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Picture.class), pictureId);
    }
    public Long addNewPicture(String picturePath){
        String sql = """
                    INSERT INTO pictures (pathtopicture) VALUES (?)
                    RETURNING id
                    """;
        return jdbcTemplate.queryForObject(sql, Long.class, picturePath);
    }
    public void addRecipeToPicture(long recipeId, long pictureId) {
        String sql = """
                    INSERT INTO recipePictures (recipeId, pictureId) VALUES (?,?)
                    """;
        jdbcTemplate.update(sql, recipeId, pictureId);
    }

    public void removeRecipeFromPictures(long recipeId){
        String sql = """
                    DELETE FROM recipePictures WHERE recipeId = ?
                    """;
        jdbcTemplate.update(sql, recipeId);
    }
    public List<String> getAllPictures(){
        String sql = """
                    SELECT pathtopicture FROM pictures
                    """;
        return jdbcTemplate.queryForList(sql, String.class);
    }

}
