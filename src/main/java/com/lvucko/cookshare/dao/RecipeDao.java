package com.lvucko.cookshare.dao;

import com.lvucko.cookshare.mappers.rowMappers.RecipeRowMapper;
import com.lvucko.cookshare.models.Category;
import com.lvucko.cookshare.models.Picture;
import com.lvucko.cookshare.models.Recipe;
import com.lvucko.cookshare.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
}
