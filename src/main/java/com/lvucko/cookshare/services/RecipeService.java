package com.lvucko.cookshare.services;

import com.lvucko.cookshare.dao.*;
import com.lvucko.cookshare.dto.RecipeCreationDto;
import com.lvucko.cookshare.dto.RecipeDetailsDto;
import com.lvucko.cookshare.dto.RecipeUpdateDto;
import com.lvucko.cookshare.enums.Role;
import com.lvucko.cookshare.exceptions.UnauthorizedException;
import com.lvucko.cookshare.mappers.RecipeMapper;
import com.lvucko.cookshare.models.Picture;
import com.lvucko.cookshare.models.Recipe;
import com.lvucko.cookshare.models.User;
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

    public List<String> getRecipePathToPictures(long recipeId){
        List<Picture> pictures =  pictureDao.getRecipePictures(recipeId);
        List<String> pathToPictures = new ArrayList<>();
        for(Picture picture : pictures){
            pathToPictures.add(picture.getPathToPicture());
        }
        return pathToPictures;
    }
    public List<RecipeDetailsDto> getRecipesDetails(List<Recipe> recipes){
        List<RecipeDetailsDto> recipeDetailsDtos = new ArrayList<>();
        for(Recipe recipe : recipes){
            List<String> picturesPath = getRecipePathToPictures(recipe.getId());
            List<String> categories = categoryDao.getRecipeCategoriesAsString(recipe.getId());
            double averageRating = ratingDao.getAverageRecipeRating(recipe.getId());
            recipeDetailsDtos.add(recipeMapper.mapToDetails(recipe, userDao.getUserById(recipe.getUserId()), picturesPath, categories, averageRating));
        }
        return recipeDetailsDtos;
    }
    public List<RecipeDetailsDto> getAllRecipes(){
        List<Recipe> recipes = recipeDao.getAllRecipes();
        return getRecipesDetails(recipes);
    }

    public RecipeDetailsDto getRecipeById(long recipeId){
        Recipe recipe = recipeDao.getRecipeById(recipeId);
        List<String> picturesPath = getRecipePathToPictures(recipe.getId());
        List<String> categories = categoryDao.getRecipeCategoriesAsString(recipe.getId());
        double averageRating = ratingDao.getAverageRecipeRating(recipe.getId());
        return recipeMapper.mapToDetails(recipe, userDao.getUserById(recipe.getUserId()), picturesPath, categories, averageRating);
    }
    public long addNewRecipe(RecipeCreationDto recipe){
        long recipeId = recipeDao.addNewRecipe(recipe);
        List <Long> categoryIds = recipe.getCategories();
        List <Long> pictures = recipe.getPictureIds();
        for(Long categoryId : categoryIds){
            categoryDao.addRecipeToCategory(recipeId, categoryId);
        }
        for(Long picture : pictures){
            pictureDao.addRecipeToPicture(recipeId, picture);
        }
        return recipeId;
    }
    public void removeRecipe(long recipeId, Long userId){
        Recipe recipe = recipeDao.getRecipeById(recipeId);
        User user = userDao.getUserById(userId);
        if(recipe.getUserId() == userId || user.getRole() == Role.ADMIN || user.getRole() == Role.MODERATOR){
            categoryDao.removeRecipeFromAllCategories(recipeId);
            pictureDao.removeRecipeFromPictures(recipeId);
            commentDao.deleteAllCommentsFromRecipe(recipeId);
            ratingDao.deleteAllRecipeRatings(recipeId);
            recipeDao.removeRecipe(recipeId);
        }
        else throw new UnauthorizedException("Unauthorized to delete");
    }
    public void updateRecipe(RecipeUpdateDto recipe, Long userId){
        Recipe oldRecipe = recipeDao.getRecipeById(recipe.getId());
        User user = userDao.getUserById(userId);
        if(oldRecipe.getUserId() == userId || user.getRole() == Role.ADMIN || user.getRole() == Role.MODERATOR){
            List <Long> categoryIds = recipe.getCategories();
            List <Long> pictures = recipe.getPictureIds();
            if(!pictures.isEmpty())
                pictureDao.removeRecipeFromPictures(recipe.getId());
            categoryDao.removeRecipeFromAllCategories(recipe.getId());
            for(Long categoryId : categoryIds){
                categoryDao.addRecipeToCategory(recipe.getId(), categoryId);
            }
            for(Long picture : pictures){
                pictureDao.addRecipeToPicture(recipe.getId(), picture);
            }
            recipeDao.updateRecipe(recipe);
        }
        else
            throw new UnauthorizedException("Unauthorized to edit");
    }
    public List<RecipeDetailsDto> getAllUserRecipes(long userId){
        List<Recipe> recipes = recipeDao.getAllRecipesFromUser(userId);
        return getRecipesDetails(recipes);
    }

    public List<RecipeDetailsDto> getLatestRecipesByCategory(long count, long categoryId){
        List<Recipe> recipes;
        if(categoryId != 0){
            recipes = recipeDao.getLatestRecipesByCategory(count, categoryId);
        }
        else{
            recipes = recipeDao.getLatestRecipes(count);
        }
        return getRecipesDetails(recipes);
    }


    public List<RecipeDetailsDto> getBestRecipesByCategory(long count, long categoryId){
        List<Recipe> recipes;
        if(categoryId != 0){
            recipes = recipeDao.getBestRecipesByCategory(count, categoryId);
        }
        else{
            recipes = recipeDao.getBestRecipes(count);
        }
        return getRecipesDetails(recipes);
    }

    public List<RecipeDetailsDto> getLeastRatedRecipesByCategory(long count, long categoryId){
        List<Recipe> recipes;
        if(categoryId != 0){
            recipes = recipeDao.getLeastRatedRecipesByCategory(count, categoryId);
        }
        else{
            recipes = recipeDao.getLeastRatedRecipes(count);
        }
        return getRecipesDetails(recipes);
    }
}
