package com.blogapp.api.repository;

import com.blogapp.api.entity.Category;
import com.blogapp.api.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByCategoryId(Long categoryId);
    Page<Post> findAll(Pageable pageable);
}
