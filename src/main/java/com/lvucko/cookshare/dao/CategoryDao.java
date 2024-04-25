package com.lvucko.cookshare.dao;

import com.lvucko.cookshare.models.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Category> getAllCategories(){
        String sql = """
                    SELECT * FROM categories
                    """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }
    public boolean hasRecipeCategory(long recipeId, String category){
        String sql = "SELECT EXISTS(SELECT * FROM "+category+" WHERE "+category+".recipeId = ?);";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, recipeId));
    }

    public List<Category>getRecipeCategories(long recipeId){
        List<Category> allCategories = getAllCategories();
        List<Category> recipeCategories = new ArrayList<>();
        for(Category category : allCategories){
            if(hasRecipeCategory(recipeId,category.getCategoryName())){
                recipeCategories.add(category);
            }
        }
        return recipeCategories;
    }

    public List<String>getRecipeCategoriesAsString(long recipeId){
        List<Category> allCategories = getAllCategories();
        List<Category> recipeCategories = new ArrayList<>();
        for(Category category : allCategories){
            if(hasRecipeCategory(recipeId,category.getCategoryName())){
                recipeCategories.add(category);
            }
        }
        List<String> categoriesString = new ArrayList<>();
        for(Category category: recipeCategories){
            categoriesString.add(category.getCategoryName());
        }
        return categoriesString;
    }
}
