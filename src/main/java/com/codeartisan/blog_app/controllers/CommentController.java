package com.codeartisan.blog_app.controllers;

import com.codeartisan.blog_app.models.ApiResponse;
import com.codeartisan.blog_app.models.CommentDto;
import com.codeartisan.blog_app.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable("postId") long postId){
        CommentDto createComment = commentService.createComment(commentDto,postId);
        return new ResponseEntity<>(createComment, HttpStatus.CREATED);

    }
    @DeleteMapping("/post/comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") long id){
        commentService.deleteComment(id);
        return ResponseEntity.ok(new ApiResponse(200,"Category Deleted Succesfully",true));
    }

}
