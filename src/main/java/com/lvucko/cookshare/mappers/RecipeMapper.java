package com.lvucko.cookshare.mappers;

import com.lvucko.cookshare.dto.RecipeDetailsDto;
import com.lvucko.cookshare.models.Recipe;
import com.lvucko.cookshare.models.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeMapper {
    public RecipeDetailsDto mapToDetails(Recipe recipe, User user, List<String> pathToPicture, List<String> categories, double averageRating, Long numberOfFavourites){
        return RecipeDetailsDto.builder()
                .id(recipe.getId())
                .userId(recipe.getUserId())
                .username(user.getUsername())
                .title(recipe.getTitle())
                .creationDate(recipe.getCreationDate())
                .shortDescription(recipe.getShortDescription())
                .longDescription(recipe.getLongDescription())
                .pathToPictures(pathToPicture)
                .categories(categories)
                .averageRating(averageRating)
                .numberOfFavourites(numberOfFavourites)
                .build();

    }
}
