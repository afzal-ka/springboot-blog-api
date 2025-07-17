package com.blogapp.api.mapper;

import com.blogapp.api.dto.PostRequest;
import com.blogapp.api.dto.PostResponse;
import com.blogapp.api.entity.Category;
import com.blogapp.api.entity.Post;
import com.blogapp.api.entity.User;

public class PostMapper {

    public static Post mapToEntity(PostRequest dto, User user, Category category){
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setContent(dto.getContent());
        post.setCategory(category);
        post.setUser(user);
        return post;

    }

    public static PostResponse mapToResponse(Post post){
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .content(post.getContent())
                .categoryName(post.getCategory().getName())
                .author(post.getUser().getUsername())
                .build();
    }

    public static void updateEntity(Post post, PostRequest dto, Category category){
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setContent(dto.getContent());
        post.setCategory(category);
    }

}
