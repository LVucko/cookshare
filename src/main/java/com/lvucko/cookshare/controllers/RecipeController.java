package com.lvucko.cookshare.controllers;

import com.lvucko.cookshare.dto.*;
import com.lvucko.cookshare.security.JwtService;
import com.lvucko.cookshare.services.CommentService;
import com.lvucko.cookshare.services.FavouriteService;
import com.lvucko.cookshare.services.RatingService;
import com.lvucko.cookshare.services.RecipeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final CommentService commentService;
    private final RatingService ratingService;
    private final JwtService jwtService;
    private final FavouriteService favouriteService;

    @GetMapping
    public ResponseEntity<List<RecipeDetailsDto>> getRecipes() throws SQLException {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDetailsDto> getRecipe(@PathVariable("id") Long recipeId) throws SQLException {
        return ResponseEntity.ok(recipeService.getRecipeById(recipeId));
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<List<RecipeDetailsDto>> getRecipesFromUser(@PathVariable("id") Long userId) throws SQLException{
        return ResponseEntity.ok(recipeService.getAllUserRecipes(userId));
    }
    @GetMapping("/user/{id}/favourites")
    public ResponseEntity<List<RecipeDetailsDto>> getFavouriteRecipesFromUser(@PathVariable("id")Long userId){
        return  ResponseEntity.ok(recipeService.getUserFavouriteRecipes(userId));
    }

    @GetMapping("/latest/{count}/category/{categoryId}")
    public ResponseEntity<List<RecipeDetailsDto>> getLatestRecipesByCategory(@PathVariable("count") Long recipeCount, @PathVariable("categoryId") Long categoryId) throws SQLException {
        return ResponseEntity.ok(recipeService.getLatestRecipesByCategory(recipeCount, categoryId));
    }
    @GetMapping("/best/{count}/category/{categoryId}")
    public ResponseEntity<List<RecipeDetailsDto>> getBestRecipesByCategory(@PathVariable("count") Long recipeCount, @PathVariable("categoryId") Long categoryId) throws SQLException {
        return ResponseEntity.ok(recipeService.getBestRecipesByCategory(recipeCount, categoryId));
    }
    @GetMapping("/least/{count}/category/{categoryId}")
    public ResponseEntity<List<RecipeDetailsDto>> getLeastRatedRecipesByCategory(@PathVariable("count") Long recipeCount, @PathVariable("categoryId") Long categoryId) throws SQLException {
        return ResponseEntity.ok(recipeService.getLeastRatedRecipesByCategory(recipeCount, categoryId));
    }

    @PostMapping
    public ResponseEntity<Long> addNewRecipe(@RequestHeader HttpHeaders headers, @RequestBody RecipeCreationDto recipe) throws SQLException{
        recipe.setUserId(jwtService.extractClaim(jwtService.extractTokenFromHeaders(headers), claims -> claims.get("UserId", Long.class)));
        return ResponseEntity.ok(recipeService.addNewRecipe(recipe));
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateRecipe(@RequestHeader HttpHeaders headers, @RequestBody RecipeUpdateDto recipe) throws SQLException{
        Long userId = jwtService.extractClaim(jwtService.extractTokenFromHeaders(headers), claims -> claims.get("UserId", Long.class));
        recipeService.updateRecipe(recipe, userId);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> removeRecipe(@RequestHeader HttpHeaders headers, @PathVariable("id") Long recipeId) throws  SQLException{
        Long userId = jwtService.extractClaim(jwtService.extractTokenFromHeaders(headers), claims -> claims.get("UserId", Long.class));
        recipeService.removeRecipe(recipeId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDetailsDto>> getRecipeComments(@PathVariable("id") Long recipeId){
        return ResponseEntity.ok(commentService.getAllRecipeComments(recipeId));
    }
    @PostMapping("/{id}/comments")
    public ResponseEntity<Long> addComment(@RequestHeader HttpHeaders headers, @RequestBody CommentCreationDto comment){
        comment.setUserId(jwtService.extractClaim(jwtService.extractTokenFromHeaders(headers), claims -> claims.get("UserId", Long.class)));
        return ResponseEntity.ok(commentService.addNewComment(comment));
    }
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}/comments")
    public ResponseEntity<HttpStatus> deleteAllCommentsFromRecipe(@PathVariable("id") long recipeId){
        commentService.deleteAllCommentsFromRecipe(recipeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{recipeId}/favourite")
    public ResponseEntity<Boolean> isRecipeUserFavourite(@RequestHeader HttpHeaders headers, @PathVariable("recipeId") Long recipeId){
        Long userId = (jwtService.extractClaim(jwtService.extractTokenFromHeaders(headers), claims -> claims.get("UserId", Long.class)));
        return ResponseEntity.ok(favouriteService.isFavourite(userId, recipeId));
    }
    @PostMapping("/{recipeId}/favourite")
    public ResponseEntity<HttpStatus> addRecipeToFavourites(@RequestHeader HttpHeaders headers, @PathVariable("recipeId") Long recipeId){
        Long userId = (jwtService.extractClaim(jwtService.extractTokenFromHeaders(headers), claims -> claims.get("UserId", Long.class)));
        favouriteService.addToFavourites(userId, recipeId);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{recipeId}/favourite")
    public ResponseEntity<HttpStatus> removeRecipeFromFavourites(@RequestHeader HttpHeaders headers, @PathVariable("recipeId") Long recipeId){
        Long userId = (jwtService.extractClaim(jwtService.extractTokenFromHeaders(headers), claims -> claims.get("UserId", Long.class)));
        favouriteService.removeFromFavourites(userId, recipeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{recipeId}/rating/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable("recipeId") Long recipeId){
        return ResponseEntity.ok(ratingService.getAverageRecipeRating(recipeId));
    }
    @GetMapping("{recipeId}/rating")
    public ResponseEntity<Long> getUserRecipeRating(@RequestHeader HttpHeaders headers, @PathVariable("recipeId") Long recipeId){
        Long userId = jwtService.extractClaim(jwtService.extractTokenFromHeaders(headers), claims -> claims.get("UserId", Long.class));
        return ResponseEntity.ok(ratingService.getUserRecipeRating(userId, recipeId));
    }
    @PostMapping("{recipeId}/rating")
    public ResponseEntity<Long> addNewRating(@RequestHeader HttpHeaders headers, @RequestBody RatingCreationDto rating){
        rating.setUserId(jwtService.extractClaim(jwtService.extractTokenFromHeaders(headers), claims -> claims.get("UserId", Long.class)));
        return ResponseEntity.ok(ratingService.addNewRating(rating));
    }
    @PutMapping("{recipeId}/rating")
    public ResponseEntity<Long> updateRating(@RequestHeader HttpHeaders headers, @RequestBody RatingCreationDto rating){
        rating.setUserId(jwtService.extractClaim(jwtService.extractTokenFromHeaders(headers), claims -> claims.get("UserId", Long.class)));
        ratingService.updateRating(rating);
        return ResponseEntity.ok().build();
    }

}
