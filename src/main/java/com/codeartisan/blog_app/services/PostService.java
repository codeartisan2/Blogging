package com.codeartisan.blog_app.services;
import com.codeartisan.blog_app.entities.Post;
import com.codeartisan.blog_app.models.PostDto;
import com.codeartisan.blog_app.models.PostResponse;

import java.util.List;

public interface PostService {

    // create post
    PostDto createPost(PostDto postDto,Long userId,Long categoryId);

    // update post

    PostDto updatePost(PostDto postDto,Long id);

    // delete
    void deletePost(Long id);

    // get all post
    PostResponse getAllPost(Integer pageNum, Integer pageSize,String sortBy,String sortDir);

    // get single post by id
    PostDto getPostById(Long id);

    // get Post by category
    List<PostDto> getPostByCategory(Long categoryId);
    // get Post by User
    List<PostDto> getPostByUser(Long userId);

    // Search
    List<PostDto> searchByKeyword(String str);
}
