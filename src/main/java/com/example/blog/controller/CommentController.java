package com.example.blog.controller;

import com.example.blog.payload.CommentDto;
import com.example.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") long id, @Valid  @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(id, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable("postId") long postId) {
        return commentService.getAllCommentsByPostId(postId);
    }

    @GetMapping("/post/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") Long postId,
                                                     @PathVariable("id") Long commentId) {
        CommentDto commentDto = commentService.getCommentById(postId, commentId);
        return ResponseEntity.ok(commentDto);
    }

    @PutMapping("/post/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable("postId") long postId,
                                                        @PathVariable("id") long commentId,
                                                        @Valid @RequestBody CommentDto commentDto) {
        CommentDto updateComment = commentService.updateComment(commentDto, postId, commentId);
        return new ResponseEntity<>(updateComment, HttpStatus.OK);
    }

    @DeleteMapping("/post/{postId}/comments/{id}")
    public String deletePostById(@PathVariable("postId") long postId,
                                 @PathVariable("id") long commentId) {
        return commentService.deleteCommentById(postId, commentId);
    }
}
