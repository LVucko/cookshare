package com.lvucko.cookshare.controllers;

import com.lvucko.cookshare.dto.*;
import com.lvucko.cookshare.security.JwtService;
import com.lvucko.cookshare.services.CommentService;
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
    @GetMapping
    public ResponseEntity<List<RecipeDetailsDto>> getRecipes() throws SQLException {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }
    @GetMapping("/latest/{count}")
    public ResponseEntity<List<RecipeDetailsDto>> getLatestRecipes(@PathVariable("count") Long recipeCount) throws SQLException {
        return ResponseEntity.ok(recipeService.getMostRecentRecipes(recipeCount));
    }
    @GetMapping("/best/{count}")
    public ResponseEntity<List<RecipeDetailsDto>> getBestRecipes(@PathVariable("count") Long recipeCount) throws SQLException {
        return ResponseEntity.ok(recipeService.getBestRecipes(recipeCount));
    }
    @GetMapping("/least/{count}")
    public ResponseEntity<List<RecipeDetailsDto>> getLeastRatedRecipes(@PathVariable("count") Long recipeCount) throws SQLException {
        return ResponseEntity.ok(recipeService.getLeastRatedRecipes(recipeCount));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDetailsDto> getRecipe(@PathVariable("id") Long recipeId) throws SQLException {
        return ResponseEntity.ok(recipeService.getRecipeById(recipeId));
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<List<RecipeDetailsDto>> getRecipesFromUser(@PathVariable("id") Long userId) throws SQLException{
        return ResponseEntity.ok(recipeService.getAllUserRecipes(userId));
    }
    @PostMapping
    public ResponseEntity<Long> addNewRecipe(@RequestHeader HttpHeaders headers, @RequestBody RecipeCreationDto recipe) throws SQLException{
        recipe.setUserId(jwtService.extractClaim(jwtService.extractTokenFromHeaders(headers), claims -> claims.get("UserId", Long.class)));
        return ResponseEntity.ok(recipeService.addNewRecipe(recipe));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> removeRecipe(@PathVariable("id") Long recipeId) throws  SQLException{
        recipeService.removeRecipe(recipeId);
        return ResponseEntity.ok().build();
    }
    @PutMapping
    public ResponseEntity<HttpStatus> updateRecipe(@RequestBody RecipeDetailsDto recipe) throws SQLException{
        recipeService.updateRecipe(recipe);
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
    @GetMapping("{recipeId}/rating/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable("recipeId") Long recipeId){
        return ResponseEntity.ok(ratingService.getAverageRecipeRating(recipeId));
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
    @GetMapping("{recipeId}/rating")
    public ResponseEntity<Long> getUserRecipeRating(@RequestHeader HttpHeaders headers, @PathVariable("recipeId") Long recipeId){
        Long userId = jwtService.extractClaim(jwtService.extractTokenFromHeaders(headers), claims -> claims.get("UserId", Long.class));
        return ResponseEntity.ok(ratingService.getUserRecipeRating(userId, recipeId));
    }
}
