package com.blogapp.api.service.impl;

import com.blogapp.api.dto.CategoryRequest;
import com.blogapp.api.dto.CategoryResponse;
import com.blogapp.api.entity.Category;
import com.blogapp.api.entity.User;
import com.blogapp.api.exceptions.ResourceNotFoundException;
import com.blogapp.api.mapper.CategoryMapper;
import com.blogapp.api.repository.CategoryRepository;
import com.blogapp.api.repository.UserRepository;
import com.blogapp.api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest, String username) {

        User user = userRepository.findByUsernameOrEmail(username,username)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        Category category = CategoryMapper.mapToEntity(categoryRequest);
        category.setUser(user);
        return CategoryMapper.mapToResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId, String username) {

        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));

        if(!user.getId().equals(category.getUser().getId()) && !user.getRoles().stream().anyMatch(role->role.getName().equals("ROLE_ADMIN"))){
            throw new ResourceNotFoundException("You don't have this category");
        }

        return CategoryMapper.mapToResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories(String username) {

        User user = userRepository.findByUsernameOrEmail(username,username)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        if(user.getRoles().stream().anyMatch(role->role.getName().equals("ROLE_ADMIN"))){
            List<Category> categories = categoryRepository.findAll();
            return categories.stream()
                    .map(CategoryMapper::mapToResponse)
                    .collect(Collectors.toList());
        }

        List<Category> categories = categoryRepository.findByUser(user);
        return categories.stream()
                .map(CategoryMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse updateCategory(Long Id, CategoryRequest categoryRequest, String username) {

        User user = userRepository.findByUsernameOrEmail(username,username)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        Category category = categoryRepository.findById(Id)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "id", Id));

        if(!user.getId().equals(category.getUser().getId()) && !user.getRoles().stream().anyMatch(role->role.getName().equals("ROLE_ADMIN"))){
            throw new ResourceNotFoundException("You cannot update this category.");
        }

        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        Category updated = categoryRepository.save(category);
        return CategoryMapper.mapToResponse(updated);
    }

    @Override
    public void deleteCategory(Long categoryId,String username) {

        User user = userRepository.findByUsernameOrEmail(username,username)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));

        if(!user.getId().equals(category.getUser().getId()) && !user.getRoles().stream().anyMatch(role->role.getName().equals("ROLE_ADMIN"))){
            throw new ResourceNotFoundException("You cannot update the category");
        }
        categoryRepository.delete(category);
    }
}
