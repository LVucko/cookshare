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

    //@GetMapping - višak je /all, jer je već definiran u @RequestMapping("api/recipes"). Na osnovnoj putanji uvijek stavljas mnozinu i onda samo s @GetMapping imas rutu koja ti dohvaca sve
    @GetMapping("/all")
    public ResponseEntity<List<RecipeDetailsDto>> getRecipes() throws SQLException {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }
    @GetMapping("/latest")
    public ResponseEntity<List<RecipeDetailsDto>> getLatestRecipes() throws SQLException {
        return ResponseEntity.ok(recipeService.getMostRecentRecipes(10));
    }
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDetailsDto> getRecipe(@PathVariable("id") Long recipeId) throws SQLException {
        return ResponseEntity.ok(recipeService.getRecipeById(recipeId));
    }
    @GetMapping("/userrecipes/{id}")
    public ResponseEntity<List<RecipeDetailsDto>> getRecipesFromUser(@PathVariable("id") Long userId) throws SQLException{
        return ResponseEntity.ok(recipeService.getAllUserRecipes(userId));
    }
    //@PostMapping - višak je /new, na osnovnoj putanji ti ide kreiranje novog resursa, ali nikad na bulk insert nego uvijek jedan
    @PostMapping("/new")
    public ResponseEntity<Long> addNewRecipe(@RequestBody RecipeCreationDto recipe) throws SQLException{
        return ResponseEntity.ok(recipeService.addNewRecipe(recipe));
    }
    //@DeleteMapping("/{id}") - ne treba ti dodatno naglašavat u putanji da je delete, jer je to već definirano u @DeleteMapping, a na istoj ruti mozes imat i get i post i put i delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeRecipe(@PathVariable("id") Long recipeId) throws  SQLException{
        recipeService.removeRecipe(recipeId);
        //Nezz jel ti ovo samo dok si testiras, ali nije dobro vracat samo string, generalno što se tice REST API-a, uvijek vracas status code i eventualno neki objekt/podatak u ovisnosti o featuru.
        // U ovom slucaju je dovoljno vratit samo status code 200 OK, ko radi frontu nece sigurno iskoristit takav string nego ce gledat po status kodu jel sve proslo kako treba
        return ResponseEntity.ok("deleted");
    }
    //@PutMapping("/{id}") - isto ko za delete gore
    @PutMapping("/update")
    public ResponseEntity<String> updateRecipe(@RequestBody RecipeDetailsDto recipe) throws SQLException{
        recipeService.updateRecipe(recipe);
        //Isto ko i za delete gore
        return ResponseEntity.ok("updated");
    }
}
