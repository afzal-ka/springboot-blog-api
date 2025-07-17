package com.blogapp.api.mapper;

import com.blogapp.api.dto.CategoryRequest;
import com.blogapp.api.dto.CategoryResponse;
import com.blogapp.api.entity.Category;

public class CategoryMapper {

    public static Category mapToEntity(CategoryRequest dto){
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }

    public static CategoryResponse mapToResponse(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public static void updateEntity(Category category, CategoryRequest dto){
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
    }
}
