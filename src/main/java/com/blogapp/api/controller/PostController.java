package com.blogapp.api.controller;

import com.blogapp.api.dto.PostPageResponse;
import com.blogapp.api.dto.PostRequest;
import com.blogapp.api.dto.PostResponse;
import com.blogapp.api.service.PostService;
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
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "Posts", description = "Operations related to blog posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestBody PostRequest postRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        PostResponse createdPost = postService.createPost(postRequest, userDetails.getUsername());
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostPageResponse> getAllPosts(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        return ResponseEntity.ok(postService.getAllPosts(pageNo,pageSize,sortBy,sortDir));
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostResponse>> getPostsByCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(postService.getPostsByCategory(categoryId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostRequest postRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        return ResponseEntity.ok(postService.updatePost(id, postRequest,userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails){
        postService.deletePost(id,userDetails.getUsername());
        return ResponseEntity.ok("Post deleted successfully");
    }

}
