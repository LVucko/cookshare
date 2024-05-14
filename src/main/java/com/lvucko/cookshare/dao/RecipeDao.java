package com.lvucko.cookshare.dao;

import com.lvucko.cookshare.dto.RecipeCreationDto;
import com.lvucko.cookshare.dto.RecipeDetailsDto;
import com.lvucko.cookshare.models.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecipeDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Recipe> getAllRecipes(){
        String sql = """
                     SELECT * FROM recipes
                     """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Recipe.class));
    }
    public Recipe getRecipeById(long recipeId){
        String sql = """
                    SELECT * FROM recipes WHERE recipes.id = ?
                    """;
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Recipe.class), recipeId);
    }
    public long addNewRecipe(RecipeCreationDto recipe){
        String sql = """
                    INSERT INTO recipes(userId, creationDate, title, shortDescription, longDescription)
                    VALUES (?, CURRENT_TIMESTAMP, ?, ?, ?)
                    RETURNING id
                    """;
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong(1), recipe.getUserId(), recipe.getTitle(), recipe.getShortDescription(), recipe.getLongDescription());
    }

    public void removeRecipe(long recipeId) {
        String sql = """
                    DELETE FROM recipes WHERE recipes.id = ?
                    """;
        jdbcTemplate.update(sql, recipeId);
    }
    public void updateRecipe(RecipeDetailsDto recipe){
        String sql = """
                    UPDATE recipes
                    SET title = ?,
                    shortDescription = ?,
                    longDescription  = ?
                    WHERE id = ?;
                    """;
        jdbcTemplate.update(sql, recipe.getTitle(), recipe.getShortDescription(), recipe.getLongDescription(), recipe.getId());
    }
    public List<Recipe> getAllRecipesFromUser(long userId){
        String sql = """
                    SELECT * FROM recipes WHERE recipes.userId = ?
                    """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Recipe.class), userId);
    }
    public List<Recipe> getLatestRecipes(long count){
        String sql = """
                    SELECT * FROM recipes ORDER BY id DESC LIMIT ?
                    """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Recipe.class), count);
    }
}
