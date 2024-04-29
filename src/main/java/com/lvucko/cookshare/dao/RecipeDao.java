package com.lvucko.cookshare.dao;

import com.lvucko.cookshare.dto.RecipeCreationDto;
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
}
