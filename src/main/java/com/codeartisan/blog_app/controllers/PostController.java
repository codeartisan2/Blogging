package com.codeartisan.blog_app.controllers;

import com.codeartisan.blog_app.configs.AppConstants;
import com.codeartisan.blog_app.models.ApiResponse;
import com.codeartisan.blog_app.models.PostDto;
import com.codeartisan.blog_app.models.PostResponse;
import com.codeartisan.blog_app.services.FileService;
import com.codeartisan.blog_app.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    private final FileService fileService;

    public PostController(PostService postService, FileService fileService) {
        this.postService = postService;
        this.fileService = fileService;
    }


    // create Post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Long userId, @PathVariable Long categoryId) {
        PostDto savedPostDto = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(savedPostDto, HttpStatus.CREATED);
    }

    // update Post
    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Long postId) {
        PostDto savedPostDto = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(savedPostDto, HttpStatus.CREATED);
    }

    // get all post
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                   @RequestParam(value = "sort", defaultValue = "id", required = false) String sortBy,
                                                   @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        PostResponse postDtos = postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    // get post by id
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    // delete post by id
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> deletePostById(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse(200, "Post Deleted Succesfully", true), HttpStatus.OK);

    }

    // get post by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Long userId) {
        List<PostDto> posts = postService.getPostByUser(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // Get Post By Category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Long categoryId) {
        List<PostDto> posts = postService.getPostByCategory(categoryId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // search post
    @GetMapping("/posts/search/{str}")
    public ResponseEntity<List<PostDto>> searchPost(@PathVariable String str) {
        List<PostDto> postDtos = postService.searchByKeyword(str);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    // upload image
    @Value("${project.image}")
    private String path;
    @PostMapping("post/image/upload/{postId}")
    public ResponseEntity<PostDto> fileUpload(@RequestParam("image") MultipartFile image,@PathVariable("postId") long postId) throws IOException {
//        try {
        PostDto postDto = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path,image);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(new FileResponse(null,"File Not Uploaded due to error"),HttpStatus.INTERNAL_SERVER_ERROR);
//
//        }
        postDto.setImageName(fileName);
        PostDto updatedPost = postService.updatePost(postDto,postId);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }
    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImaage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resouce = fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resouce,response.getOutputStream());
    }
}
