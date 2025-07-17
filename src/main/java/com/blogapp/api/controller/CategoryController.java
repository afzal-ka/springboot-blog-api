package com.blogapp.api.controller;

import com.blogapp.api.dto.CategoryRequest;
import com.blogapp.api.dto.CategoryResponse;
import com.blogapp.api.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Operations related to post categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest categoryRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        CategoryResponse createdCategory = categoryService.createCategory(categoryRequest, userDetails.getUsername());
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(categoryService.getAllCategories(userDetails.getUsername()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(categoryService.getCategoryById(id, userDetails.getUsername()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest categoryRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        return ResponseEntity.ok(categoryService.updateCategory(id,categoryRequest, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        categoryService.deleteCategory(id, userDetails.getUsername());
        return ResponseEntity.ok("Category deleted successfully");
    }

}
