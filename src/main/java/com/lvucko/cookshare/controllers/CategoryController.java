package com.lvucko.cookshare.controllers;

import com.lvucko.cookshare.models.Category;
import com.lvucko.cookshare.security.JwtService;
import com.lvucko.cookshare.services.CategoryService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final JwtService jwtService;
    @GetMapping
    public ResponseEntity<List<Category>> getCategories(@RequestHeader HttpHeaders headers) throws SQLException{
        return ResponseEntity.ok(categoryService.getCategories());
    }
    @PostMapping("/{category}")
    public ResponseEntity<HttpStatus> createCategory(@PathVariable("category") String categoryName) throws SQLException {
        categoryService.addCategory(categoryName);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("id") Long categoryId) throws SQLException{
        categoryService.removeCategory(categoryId);
        return ResponseEntity.ok().build();
    }

}
