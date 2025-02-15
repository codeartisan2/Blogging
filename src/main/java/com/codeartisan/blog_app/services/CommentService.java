package com.codeartisan.blog_app.services;

import com.codeartisan.blog_app.models.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Long postId);

    void deleteComment(Long id);
}
