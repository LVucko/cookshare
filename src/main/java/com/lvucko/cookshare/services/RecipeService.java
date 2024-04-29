package com.lvucko.cookshare.services;

import com.lvucko.cookshare.dao.CategoryDao;
import com.lvucko.cookshare.dao.PictureDao;
import com.lvucko.cookshare.dao.RecipeDao;
import com.lvucko.cookshare.dao.UserDao;
import com.lvucko.cookshare.dto.RecipeCreationDto;
import com.lvucko.cookshare.dto.RecipeDetailsDto;
import com.lvucko.cookshare.mappers.RecipeMapper;
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

    public List<RecipeDetailsDto> getAllRecipes(){
        List<Recipe> recipes = recipeDao.getAllRecipes();
        List<RecipeDetailsDto> recipeDetailsDtos = new ArrayList<>();
        for(Recipe recipe : recipes){
            List<String> picturesPath = pictureDao.getRecipePathToPictures(recipe.getId());
            List<String> categories = categoryDao.getRecipeCategoriesAsString(recipe.getId());
            recipeDetailsDtos.add(recipeMapper.mapToDetails(recipe, userDao.getUserById(recipe.getUserId()), picturesPath, categories));
        }
        return recipeDetailsDtos;
    }
    public RecipeDetailsDto getRecipeById(long recipeId){
        Recipe recipe = recipeDao.getRecipeById(recipeId);
        List<String> picturesPath = pictureDao.getRecipePathToPictures(recipe.getId());
        List<String> categories = categoryDao.getRecipeCategoriesAsString(recipe.getId());
        return recipeMapper.mapToDetails(recipe, userDao.getUserById(recipe.getUserId()), picturesPath, categories);
    }
    public long addNewRecipe(RecipeCreationDto recipe){
        long recipeId = recipeDao.addNewRecipe(recipe);
        List <String> categories = recipe.getCategories();
        List <String> pathToPictures = recipe.getPathToPictures();
        for(String category : categories){
            categoryDao.addRecipeToCategory(recipeId, category);
        }
        for(String pathToPicture : pathToPictures){
            pictureDao.addRecipeToPicture(recipeId, pathToPicture);
        }
        return recipeId;

    }
    public void removeRecipe(long recipeId){
        List<String> categories = categoryDao.getRecipeCategoriesAsString(recipeId);
        for(String category: categories){
            categoryDao.removeRecipeFromCategory(recipeId, category);
        }
        pictureDao.removeRecipeFromPictures(recipeId);
        recipeDao.removeRecipe(recipeId);
    }
    public void updateRecipe(RecipeDetailsDto recipe){
        pictureDao.removeRecipeFromPictures(recipe.getId());
        List<String> categories = categoryDao.getRecipeCategoriesAsString(recipe.getId());
        for(String category: categories){
            categoryDao.removeRecipeFromCategory(recipe.getId(), category);
        }
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
}
