package com.lvucko.cookshare.controllers;


import com.lvucko.cookshare.dto.RatingCreationDto;
import com.lvucko.cookshare.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/ratings")
public class RatingController {
    private final RatingService ratingService;

    @GetMapping("/r/{id}")
    public ResponseEntity<Double> getAverageRecipeRating(@PathVariable("id") Long recipeId){
        return ResponseEntity.ok(ratingService.getAverageRecipeRating(recipeId));
    }
    @GetMapping("/{id1}/{id2}")
    public ResponseEntity<Long> getUserRecipeRating(@PathVariable("id1") Long userId, @PathVariable("id2") Long recipeId){
        return ResponseEntity.ok(ratingService.getUserRecipeRating(recipeId, userId));
    }
    @PostMapping("")
    public ResponseEntity<Long> addNewRating(@RequestBody RatingCreationDto rating){
        return ResponseEntity.ok(ratingService.addNewRating(rating));
    }
}
