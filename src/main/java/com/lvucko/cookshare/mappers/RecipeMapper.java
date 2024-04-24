package com.lvucko.cookshare.mappers;

import com.lvucko.cookshare.dto.RecipeDetailsDto;
import com.lvucko.cookshare.models.Recipe;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeMapper {
    public RecipeDetailsDto mapToDetails(Recipe recipe, List<String> pathToPicture, List<String> categories){
        return RecipeDetailsDto.builder()
                .id(recipe.getId())
                .userId(recipe.getUserId())
                .title(recipe.getTitle())
                .shortDescription(recipe.getShortDescription())
                .longDescription(recipe.getLongDescription())
                .pathToPictures(pathToPicture)
                .categories(categories)
                .build();

    }
}
