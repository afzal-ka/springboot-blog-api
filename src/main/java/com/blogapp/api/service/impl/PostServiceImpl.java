package com.blogapp.api.service.impl;

import com.blogapp.api.dto.PostPageResponse;
import com.blogapp.api.dto.PostRequest;
import com.blogapp.api.dto.PostResponse;
import com.blogapp.api.entity.Category;
import com.blogapp.api.entity.Post;
import com.blogapp.api.entity.User;
import com.blogapp.api.exceptions.ResourceNotFoundException;
import com.blogapp.api.mapper.PostMapper;
import com.blogapp.api.repository.CategoryRepository;
import com.blogapp.api.repository.PostRepository;
import com.blogapp.api.repository.UserRepository;
import com.blogapp.api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public PostResponse createPost(PostRequest postRequest, String username) {
        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));

        Category category =  categoryRepository.findById(postRequest.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category", "id", postRequest.getCategoryId()));

        if(!category.getUser().getId().equals(user.getId()) ){
            throw new ResourceNotFoundException("User not authorized to post in this category");
        }

        Post post = PostMapper.mapToEntity(postRequest, user, category);
        Post savedPost = postRepository.save(post);

        return PostMapper.mapToResponse(savedPost);
    }

    @Override
    public PostResponse getPostById(Long Id) {
        Post post = postRepository.findById(Id)
                .orElseThrow(()->new ResourceNotFoundException("Post", "id", Id));
        return PostMapper.mapToResponse(post);
    }

    @Override
    public PostPageResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = Sort.by(sortBy);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<PostResponse> content = posts.getContent().stream()
                .map(PostMapper::mapToResponse)
                .collect(Collectors.toList());

        PostPageResponse postPageResponse = new PostPageResponse();
        postPageResponse.setContent(content);
        postPageResponse.setPageNo(posts.getNumber());
        postPageResponse.setPageSize(posts.getSize());
        postPageResponse.setTotalElements(posts.getTotalElements());
        postPageResponse.setTotalPages(posts.getTotalPages());
        postPageResponse.setLast(posts.isLast());


        return postPageResponse;
    }

    @Override
    public List<PostResponse> getPostsByCategory(Long categoryId) {
        return postRepository.findByCategoryId(categoryId)
                .stream().map(PostMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PostResponse updatePost(Long Id, PostRequest postRequest, String username) {

        User user = userRepository.findByUsernameOrEmail(username,username)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));

        Post post = postRepository.findById(Id)
                .orElseThrow(()->new ResourceNotFoundException("Post", "id", Id));

        if(!post.getUser().getId().equals(user.getId()) && !user.getRoles().stream().anyMatch(role->role.getName().equals("ROLE_ADMIN"))){
            throw new ResourceNotFoundException("You are not authorized to update this post.");
        }
        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setContent(postRequest.getContent());

        Post updatePost = postRepository.save(post);
        return PostMapper.mapToResponse(updatePost);
    }

    @Override
    public void deletePost(Long postId, String username) {

        User user = userRepository.findByUsernameOrEmail(username,username)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));

        if(!post.getUser().getId().equals(user.getId()) && !user.getRoles().stream().anyMatch(role->role.getName().equals("ROLE_ADMIN")))  {
            throw new ResourceNotFoundException("You are not authorized to delete this post.");
        }
        postRepository.delete(post);
    }
}
