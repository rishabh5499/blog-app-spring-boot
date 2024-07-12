package com.example.blog.service;

import com.example.blog.payload.CommentDto;
import com.example.blog.payload.PostDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getAllCommentsByPostId(long postId);
    CommentDto getCommentById(Long postId, Long commentId);
    CommentDto updateComment(CommentDto commentDto, long postId, long commentId);
    String deleteCommentById(long postId, long commentId);
}
