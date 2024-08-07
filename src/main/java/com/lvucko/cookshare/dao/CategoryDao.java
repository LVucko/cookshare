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

    public List<Category>getRecipeCategories(long recipeId){
        String sql = """
                    SELECT categories.* from categories
                    INNER JOIN recipecategories
                    ON  categories.id=recipecategories.categoryid
                    WHERE recipecategories.recipeid = ?
                    """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class), recipeId);
    }

    public List<String>getRecipeCategoriesAsString(long recipeId){
        List<Category> recipeCategories = getRecipeCategories(recipeId);
        List<String> categoriesString = new ArrayList<>();
        for(Category category: recipeCategories){
            categoriesString.add(category.getName());
        }
        return categoriesString;
    }

    public void addRecipeToCategory(long recipeId, long categoryId){
        String sql = """
                INSERT INTO recipeCategories(recipeid, categoryid)
                VALUES(?, ?)
                """;
        jdbcTemplate.update(sql, recipeId, categoryId);
    }
    public void addRecipeToCategory(long recipeId, String categoryName){
        String sql = """
                INSERT INTO recipeCategories(recipeid, categoryid)
                VALUES(?, (SELECT id from categories where categories.name = ?))
                """;
        jdbcTemplate.update(sql, recipeId, categoryName);
    }

    public void removeRecipeFromCategory(long recipeId, long categoryId){
        String sql = """
                    DELETE FROM recipeCategories
                    WHERE recipeCategories.recipeId = ? AND recipeCategories.categoryId = ?
                    """;
        jdbcTemplate.update(sql, recipeId, categoryId);
    }
    public void removeRecipeFromAllCategories(long recipeId){
        String sql = """
                    DELETE FROM recipeCategories
                    WHERE recipeCategories.recipeId = ?
                    """;
        jdbcTemplate.update(sql, recipeId);
    }

    public void deleteCategory(Long categoryId){
        String sql = """
                    DELETE FROM categories WHERE categories.id = ?
                    """;
        jdbcTemplate.update(sql, categoryId);
    }
    public void removeCategoryFromAllRecipes(Long categoryId){
        String sql = """
                    DELETE FROM recipeCategories WHERE categoryid = ?
                    """;
        jdbcTemplate.update(sql, categoryId);
    }

    public void addNewCategory(String category){
        String sql ="""
                    INSERT INTO categories(name) VALUES(?)
                    """;
        jdbcTemplate.update(sql, category);
    }
}
