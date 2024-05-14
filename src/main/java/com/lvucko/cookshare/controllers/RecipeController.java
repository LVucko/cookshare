package com.lvucko.cookshare.controllers;

import com.lvucko.cookshare.dto.RecipeCreationDto;
import com.lvucko.cookshare.dto.RecipeDetailsDto;
import com.lvucko.cookshare.models.Category;
import com.lvucko.cookshare.services.RecipeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    @GetMapping("/all")
    public ResponseEntity<List<RecipeDetailsDto>> getRecipes() throws SQLException {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDetailsDto> getRecipe(@PathVariable("id") Long recipeId) throws SQLException {
        return ResponseEntity.ok(recipeService.getRecipeById(recipeId));
    }
    @PostMapping("/new")
    public ResponseEntity<Long> addNewRecipe(@RequestBody RecipeCreationDto recipe) throws SQLException{
        return ResponseEntity.ok(recipeService.addNewRecipe(recipe));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeRecipe(@PathVariable("id") Long recipeId) throws  SQLException{
        recipeService.removeRecipe(recipeId);
        return ResponseEntity.ok("deleted");
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateRecipe(@RequestBody RecipeDetailsDto recipe) throws SQLException{
        recipeService.updateRecipe(recipe);
        return ResponseEntity.ok("updated");
    }
}
