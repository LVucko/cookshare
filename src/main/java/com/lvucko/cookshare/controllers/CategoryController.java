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
    @PostMapping
    public ResponseEntity<HttpStatus> createCategory(@RequestBody String name) throws SQLException {
        categoryService.addCategory(name);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteCategory(@RequestBody Category category) throws SQLException{
        categoryService.removeCategory(category);
        return ResponseEntity.ok().build();
    }

}
