package com.example.blog.controller;

import com.example.blog.payload.CommentDto;
import com.example.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@Tag(name = "CRUD REST APIs for Comment Resource")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(
            summary = "Create Comment REST API",
            description = "Create Comment REST API is used to save Comment to DB"
    )
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") long id, @Valid  @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(id, commentDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Comment by PostID REST API",
            description = "Get Comment by PostID REST API is used to get a comment related to a particular Post"
    )
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @GetMapping("/post/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable("postId") long postId) {
        return commentService.getAllCommentsByPostId(postId);
    }

    @Operation(
            summary = "Get Comment by ID REST API",
            description = "Get Comment by ID REST API is used to get particular comment from DB"
    )
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @GetMapping("/post/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") Long postId,
                                                     @PathVariable("id") Long commentId) {
        CommentDto commentDto = commentService.getCommentById(postId, commentId);
        return ResponseEntity.ok(commentDto);
    }

    @Operation(
            summary = "Update Comment REST API",
            description = "Update Comment REST API is used to update a comment"
    )
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @PutMapping("/post/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable("postId") long postId,
                                                        @PathVariable("id") long commentId,
                                                        @Valid @RequestBody CommentDto commentDto) {
        CommentDto updateComment = commentService.updateComment(commentDto, postId, commentId);
        return new ResponseEntity<>(updateComment, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Comment REST API",
            description = "Delete Comment REST API is used to delete Comment to DB"
    )
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @DeleteMapping("/post/{postId}/comments/{id}")
    public String deletePostById(@PathVariable("postId") long postId,
                                 @PathVariable("id") long commentId) {
        return commentService.deleteCommentById(postId, commentId);
    }
}
