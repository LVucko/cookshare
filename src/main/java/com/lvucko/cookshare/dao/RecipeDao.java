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
    private final RecipeRowMapper recipeRowMapper;
    public List<Recipe> getAllRecipes(){
        String sql = """
                     SELECT * FROM recipes
                     """;
        return jdbcTemplate.query(sql, recipeRowMapper);
    }
    public Recipe getRecipeById(long recipeId){
        String sql = """
                    SELECT * FROM recipes where recipes.id = ?
                """;
        return jdbcTemplate.queryForObject(sql, recipeRowMapper, recipeId);
    }
    public List<Picture>getRecipePictures(long recipeId){
        String sql = """
                    SELECT * FROM pictures where pictures.recipeId = ?
                    """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Picture.class));
    }
    public List<Category> getAllCategories(){
        String sql = """
                    SELECT * FROM categories
                    """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }
    public boolean isCategory(String category, long recipeId){
        String sql = """
                    SELECT EXISTS(SELECT * FROM ? WHERE ?.recipeID = ?)
                    """;
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, category, category, recipeId));
    }
    public List<Category>getRecipeCategories(long recipeId){
        List<Category> allCategories = getAllCategories();
        List<Category> recipeCategories = new ArrayList<>();
        for(Category category : allCategories){
            if(isCategory(category.getCategoryName(), recipeId)){
                recipeCategories.add(category);
            }
        }
        return recipeCategories;
    }
}
