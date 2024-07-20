package com.example.blog.service.impl;

import com.example.blog.entity.Comments;
import com.example.blog.entity.Post;
import com.example.blog.exception.BlogAPIException;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.payload.CommentDto;
import com.example.blog.payload.PostDto;
import com.example.blog.payload.PostResponse;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comments comments = mapToEntity(commentDto);
        comments.setPost(getPost(postId));
        Comments newComment = commentRepository.save(comments);
        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(long postId) {
        List<Comments> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        if (!getComment(commentId).getPost().getId().equals(getPost(postId).getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return mapToDTO(getComment(commentId));
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, long postId, long commentId) {
        Comments comments = getComment(commentId);
        if (!getComment(commentId).getPost().getId().equals(getPost(postId).getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        comments.setName(commentDto.getName());
        comments.setEmail(commentDto.getEmail());
        comments.setMessageBody(commentDto.getMessageBody());
        return mapToDTO(commentRepository.save(comments));
    }

    @Override
    public String deleteCommentById(long postId, long commentId) {
        if (!getComment(commentId).getPost().getId().equals(getPost(postId).getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        commentRepository.delete(getComment(commentId));
        return "Deleted successfully";
    }


    private CommentDto mapToDTO(Comments comments) {
        return modelMapper.map(comments, CommentDto.class);
    }

    private Comments mapToEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comments.class);
    }

    private Post getPost(long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
    }

    private Comments getComment(long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
    }
}
