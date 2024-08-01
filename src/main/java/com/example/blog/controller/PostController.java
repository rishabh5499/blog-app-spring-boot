package com.example.blog.controller;

import com.example.blog.entity.Post;
import com.example.blog.payload.PostDto;
import com.example.blog.payload.PostResponse;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.PostService;
import com.example.blog.service.impl.PostServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.blog.utils.AppConstants.*;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(name = "CRUD REST APIs for Post Resource")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API is used to save Post to DB"
    )
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get All Post REST API",
            description = "Get All Post REST API is used to get Post from DB"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 OK")
    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIR, required = false) String sortDir) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    @Operation(
            summary = "Get Post by ID REST API",
            description = "Get single post from DB"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 OK")
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @Operation(
            summary = "Update Post REST API",
            description = "Update Post REST API is used to update Post in DB"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 OK")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable("id") long id) {
        return ResponseEntity.ok(postService.updatePost(postDto, id));
    }

    @Operation(
            summary = "Delete Post REST API",
            description = "Create Post REST API is used to delete Post from DB"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 OK")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id) {
        return ResponseEntity.ok(postService.deletePost(id));
    }

    @Operation(
            summary = "Get Post by category REST API",
            description = "Get Post REST API is used to get Post by category"
    )
    @ApiResponse(responseCode = "200", description = "Http Status 200 OK")
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable(value = "id") Long categoryId) {
        List<PostDto> postDtos = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(postDtos);
    }
}
