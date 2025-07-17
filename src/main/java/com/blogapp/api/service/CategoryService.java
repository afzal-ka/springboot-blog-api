package com.blogapp.api.service;

import com.blogapp.api.dto.CategoryRequest;
import com.blogapp.api.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest,String username);
    CategoryResponse getCategoryById(Long Id, String username);
    List<CategoryResponse> getAllCategories(String username);
    CategoryResponse updateCategory(Long Id, CategoryRequest categoryRequest, String username);
    void deleteCategory(Long Id, String username);
}
