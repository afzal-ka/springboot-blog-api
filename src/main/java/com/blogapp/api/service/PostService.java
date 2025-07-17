package com.blogapp.api.service;

import com.blogapp.api.dto.PostPageResponse;
import com.blogapp.api.dto.PostRequest;
import com.blogapp.api.dto.PostResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    PostResponse createPost(PostRequest postRequest, String username);
    PostResponse getPostById(Long Id);
    PostPageResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    List<PostResponse> getPostsByCategory(Long categoryId);
    PostResponse updatePost(Long Id, PostRequest postRequest, String username);
    void deletePost(Long Id, String username);
}
