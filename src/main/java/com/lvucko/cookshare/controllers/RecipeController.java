package com.lvucko.cookshare.controllers;

import com.lvucko.cookshare.dto.*;
import com.lvucko.cookshare.services.CommentService;
import com.lvucko.cookshare.services.RatingService;
import com.lvucko.cookshare.services.RecipeService;

import lombok.RequiredArgsConstructor;
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
    @GetMapping
    public ResponseEntity<List<RecipeDetailsDto>> getRecipes() throws SQLException {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }
    @GetMapping("/latest/{id}")
    public ResponseEntity<List<RecipeDetailsDto>> getLatestRecipes(@PathVariable("id") Long recipeCount) throws SQLException {
        return ResponseEntity.ok(recipeService.getMostRecentRecipes(recipeCount));
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
    public ResponseEntity<Long> addNewRecipe(@RequestBody RecipeCreationDto recipe) throws SQLException{
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
    public ResponseEntity<Long> addComment(@RequestBody CommentCreationDto comment){
        return ResponseEntity.ok(commentService.addNewComment(comment));
    }
    @DeleteMapping("/{id}/comments")
    public ResponseEntity<HttpStatus> deleteAllCommentsFromRecipe(@PathVariable("id") long recipeId){
        commentService.deleteAllCommentsFromRecipe(recipeId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("{recipeId}/rating")
    public ResponseEntity<Long> addNewRating(@RequestBody RatingCreationDto rating){
        return ResponseEntity.ok(ratingService.addNewRating(rating));
    }
    @GetMapping("{recipeId}/rating/user/{userId}")
    public ResponseEntity<Long> getUserRecipeRating(@PathVariable("recipeId") Long recipeId, @PathVariable("userId") Long userId){
        return ResponseEntity.ok(ratingService.getUserRecipeRating(userId, recipeId));
    }
}
