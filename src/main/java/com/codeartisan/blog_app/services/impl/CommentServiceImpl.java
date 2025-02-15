package com.codeartisan.blog_app.services.impl;

import com.codeartisan.blog_app.entities.Comment;
import com.codeartisan.blog_app.entities.Post;
import com.codeartisan.blog_app.exceptions.ResourceNotFoundException;
import com.codeartisan.blog_app.models.CommentDto;
import com.codeartisan.blog_app.repositories.CommentRepo;
import com.codeartisan.blog_app.repositories.PostRepo;
import com.codeartisan.blog_app.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final PostRepo postRepo;

    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepo commentRepo, PostRepo postRepo, ModelMapper modelMapper) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment = commentRepo.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", id));
        commentRepo.delete(comment);
    }
}
