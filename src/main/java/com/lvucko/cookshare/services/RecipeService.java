package com.lvucko.cookshare.services;

import com.lvucko.cookshare.dao.RecipeDao;
import com.lvucko.cookshare.dao.UserDao;
import com.lvucko.cookshare.dto.RecipeDetailsDto;
import com.lvucko.cookshare.mappers.RecipeMapper;
import com.lvucko.cookshare.models.Category;
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

    public List<RecipeDetailsDto> getAllRecipes(){
        List<Recipe> recipes = recipeDao.getAllRecipes();
        List<RecipeDetailsDto> recipeDetailsDtos = new ArrayList<>();
        for(Recipe recipe : recipes){
            List<Picture> pictures = recipeDao.getRecipePictures(recipe.getId());
            List<String> pathToPictures = new ArrayList<>();
            for(Picture picture: pictures){
                pathToPictures.add(picture.getPathToPicture());
            }
            List<Category> categories = recipeDao.getRecipeCategories(recipe.getId());
            List<String> validCategories = new ArrayList<>();
            for(Category category: categories){
                validCategories.add(category.getCategoryName());
            }
            recipeDetailsDtos.add(recipeMapper.mapToDetails(recipe, userDao.getUserById(recipe.getUserId()), pathToPictures, validCategories));
        }
        return recipeDetailsDtos;
    }
    
}
