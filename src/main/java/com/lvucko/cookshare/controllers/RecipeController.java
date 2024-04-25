package com.lvucko.cookshare.controllers;

import com.lvucko.cookshare.dto.RecipeDetailsDto;
import com.lvucko.cookshare.services.RecipeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    @GetMapping("/all")
    public ResponseEntity<List<RecipeDetailsDto>> getRecipes()
            throws SQLException {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDetailsDto> getRecipe(@PathVariable("id") Long recipeId)
            throws SQLException {
        return ResponseEntity.ok(recipeService.getRecipeById(recipeId));
    }
}
