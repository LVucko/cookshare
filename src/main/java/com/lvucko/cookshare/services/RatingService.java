package com.lvucko.cookshare.services;

import com.lvucko.cookshare.dao.RatingDao;
import com.lvucko.cookshare.dto.RatingCreationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingDao ratingDao;

    public Double getAverageRecipeRating(long recipeId) {
        return ratingDao.getAverageRecipeRating(recipeId);
    }
    public Long addNewRating(RatingCreationDto rating){
        return ratingDao.addNewRating(rating);
    }
    public Long getUserRecipeRating(long userId, long recipeId){
        try {
            return ratingDao.getUserRatingForRecipe(userId, recipeId);
        }
        catch(Exception e){
            return  -1L;
        }
    }
}
