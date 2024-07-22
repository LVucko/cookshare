package com.lvucko.cookshare.dao;

import com.lvucko.cookshare.dto.RecipeCreationDto;
import com.lvucko.cookshare.dto.RecipeDetailsDto;
import com.lvucko.cookshare.dto.RecipeUpdateDto;
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
    public Long addNewRecipe(RecipeCreationDto recipe){
        String sql = """
                    INSERT INTO recipes(userId, creationDate, title, shortDescription, longDescription)
                    VALUES (?, CURRENT_TIMESTAMP, ?, ?, ?)
                    RETURNING id
                    """;
        return jdbcTemplate.queryForObject(sql, Long.class, recipe.getUserId(), recipe.getTitle(), recipe.getShortDescription(), recipe.getLongDescription());
    }

    public void removeRecipe(long recipeId) {
        String sql = """
                    DELETE FROM recipes WHERE recipes.id = ?
                    """;
        jdbcTemplate.update(sql, recipeId);
    }
    public void updateRecipe(RecipeUpdateDto recipe){
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
    public List<Recipe> getBestRecipes(long count){
        String sql = """
                    SELECT recipes.*
                    from recipes
                    left join (select recipeid, AVG(rating) as averageRating FROM ratings GROUP BY recipeid)
                    on recipeid = recipes.id
                    order by averagerating desc NULLS LAST LIMIT ?
                    """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Recipe.class), count);
    }
    public List<Recipe> getBestRecipesByCategory(long count, long categoryId){
        String sql = """
                    SELECT recipes.*
                    from recipes
                    left join (select recipeid, AVG(rating) as averageRating FROM ratings GROUP BY recipeid)
                    on recipeid = recipes.id
                    inner join (select recipeid as categoryrecipeid FROM recipecategories WHERE categoryId=?)
                    on categoryrecipeid = recipes.id
                    order by averagerating desc NULLS LAST LIMIT ?
                    """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Recipe.class), categoryId, count);
    }
    public List<Recipe> getLeastRatedRecipes(long count){
        String sql = """
                    SELECT recipes.*
                    from recipes
                    left join (select recipeid, AVG(rating) as averageRating FROM ratings GROUP BY recipeid)
                    on recipeid = recipes.id
                    order by averagerating asc NULLS first LIMIT ?
                    """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Recipe.class), count);
    }
    public List<Recipe> getLeastRatedRecipesByCategory(long count, long categoryId){
        String sql = """
                    SELECT recipes.*
                    from recipes
                    left join (select recipeid, AVG(rating) as averageRating FROM ratings GROUP BY recipeid)
                    on recipeid = recipes.id
                    inner join (select recipeid as categoryrecipeid FROM recipecategories WHERE categoryId= ? )
                    on categoryrecipeid = recipes.id
                    order by averagerating asc NULLS first LIMIT ?
                    """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Recipe.class), categoryId, count);
    }
    public List<Recipe> getLatestRecipes(long count){
        String sql = """
                    SELECT * FROM recipes ORDER BY id DESC LIMIT ?
                    """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Recipe.class), count);
    }
    public List<Recipe> getLatestRecipesByCategory(long count, long categoryId){
        String sql = """
                    SELECT recipes.*
                    from recipes
                    inner join (select recipeid FROM recipecategories WHERE categoryId = ?)
                    on recipeid = recipes.id
                    order by creationdate desc LIMIT ?
                    """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Recipe.class),categoryId, count);
    }
    public List<Recipe> getFavouriteRecipesFromUser(Long userId){
        String sql = """
                    SELECT recipes.*
                    from recipes
                    left join (select recipeid FROM favourites GROUP BY recipeid)
                    on recipeid = recipes.id
                    inner join (select recipeid as favouriterecipeid FROM favourites WHERE userid= ? )
                    on favouriterecipeid = recipes.id
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Recipe.class), userId);
    }





}
