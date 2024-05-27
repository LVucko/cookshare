package com.lvucko.cookshare.controllers;

import com.lvucko.cookshare.models.Category;
import com.lvucko.cookshare.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping
    public ResponseEntity<List<Category>> getCategories() throws SQLException{
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
