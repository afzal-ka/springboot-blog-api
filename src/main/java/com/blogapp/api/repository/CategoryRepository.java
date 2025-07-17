package com.blogapp.api.repository;

import com.blogapp.api.entity.Category;
import com.blogapp.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByUser(User user);
}
