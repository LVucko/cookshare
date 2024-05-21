package com.lvucko.cookshare.services;

import com.lvucko.cookshare.dao.*;
import com.lvucko.cookshare.dto.RecipeCreationDto;
import com.lvucko.cookshare.dto.RecipeDetailsDto;
import com.lvucko.cookshare.mappers.RecipeMapper;
import com.lvucko.cookshare.models.Picture;
import com.lvucko.cookshare.models.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeDao recipeDao;
    private final RecipeMapper recipeMapper;
    private final UserDao userDao;
    private final PictureDao pictureDao;
    private final CategoryDao categoryDao;
    private final CommentDao commentDao;
    private final RatingDao ratingDao;
    public List<RecipeDetailsDto> getAllRecipes(){
        List<Recipe> recipes = recipeDao.getAllRecipes();
        return getRecipeDetails(recipes);
    }
    public RecipeDetailsDto getRecipeById(long recipeId){
        Recipe recipe = recipeDao.getRecipeById(recipeId);
        List<String> picturesPath = getRecipePathToPictures(recipe.getId());
        List<String> categories = categoryDao.getRecipeCategoriesAsString(recipe.getId());
        return recipeMapper.mapToDetails(recipe, userDao.getUserById(recipe.getUserId()), picturesPath, categories);
    }
    public long addNewRecipe(RecipeCreationDto recipe){
        long recipeId = recipeDao.addNewRecipe(recipe);
        List <Long> categoryIds = recipe.getCategories();
        List <String> pathToPictures = recipe.getPathToPictures();
        for(Long categoryId : categoryIds){
            categoryDao.addRecipeToCategory(recipeId, categoryId);
        }
        for(String pathToPicture : pathToPictures){
            pictureDao.addRecipeToPicture(recipeId, pathToPicture);
        }
        return recipeId;

    }
    public void removeRecipe(long recipeId){
        categoryDao.removeRecipeFromAllCategories(recipeId);
        pictureDao.removeRecipeFromPictures(recipeId);
        commentDao.deleteAllCommentsFromRecipe(recipeId);
        ratingDao.deleteAllRecipeRatings(recipeId);
        recipeDao.removeRecipe(recipeId);

    }
    public void updateRecipe(RecipeDetailsDto recipe){
        pictureDao.removeRecipeFromPictures(recipe.getId());
        categoryDao.removeRecipeFromAllCategories(recipe.getId());
        List <String> pathToPictures = recipe.getPathToPictures();
        for(String pathToPicture : pathToPictures){
            pictureDao.addRecipeToPicture(recipe.getId(), pathToPicture);
        }
        List <String> newCategories = recipe.getCategories();
        for(String category : newCategories){
            categoryDao.addRecipeToCategory(recipe.getId(), category);
        }
        recipeDao.updateRecipe(recipe);
    }
    public List<RecipeDetailsDto> getAllUserRecipes(long userId){
        List<Recipe> recipes = recipeDao.getAllRecipesFromUser(userId);
        return getRecipeDetails(recipes);

    }
    public List<RecipeDetailsDto> getMostRecentRecipes(long count){
        List<Recipe> recipes = recipeDao.getLatestRecipes(count);
        return getRecipeDetails(recipes);
    }
    public List<RecipeDetailsDto> getRecipeDetails(List<Recipe> recipes){
        List<RecipeDetailsDto> recipeDetailsDtos = new ArrayList<>();
        for(Recipe recipe : recipes){
            List<String> picturesPath = getRecipePathToPictures(recipe.getId());
            List<String> categories = categoryDao.getRecipeCategoriesAsString(recipe.getId());
            recipeDetailsDtos.add(recipeMapper.mapToDetails(recipe, userDao.getUserById(recipe.getUserId()), picturesPath, categories));
        }
        return recipeDetailsDtos;
    }

    public List<String> getRecipePathToPictures(long recipeId){
        List<Picture> pictures =  pictureDao.getRecipePictures(recipeId);
        List<String> pathToPictures = new ArrayList<>();
        for(Picture picture : pictures){
            pathToPictures.add(picture.getPathToPicture());
        }
        return pathToPictures;
    }
}
