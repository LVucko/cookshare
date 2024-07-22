package com.lvucko.cookshare.services;

import com.lvucko.cookshare.dao.FavouriteDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavouriteService {
    private final FavouriteDao favouriteDao;
    public void addToFavourites(Long userId, Long recipeId){
        favouriteDao.addToFavourites(userId, recipeId);
    }
    public void removeFromFavourites(Long userId, Long recipeId){
        favouriteDao.removeFromFavourites(userId, recipeId);
    }
    public Boolean isFavourite(Long userId, Long recipeId){
        return favouriteDao.isFavourite(userId, recipeId);
    }

}
