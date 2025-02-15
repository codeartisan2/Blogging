package com.codeartisan.blog_app.controllers;
import com.codeartisan.blog_app.models.ApiResponse;
import com.codeartisan.blog_app.models.CategoryDto;
import com.codeartisan.blog_app.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")

public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Post - Create User
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto savedCatDto = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(savedCatDto, HttpStatus.CREATED);
    }

    // Put - Update User
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("id") Long id) {
        CategoryDto updatedUserDto = categoryService.updateCategory(categoryDto, id);
        return ResponseEntity.ok(updatedUserDto);
    }
    // Get - Get All User
    @GetMapping("/")
    public  ResponseEntity<List<CategoryDto>> getAllCategory(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    // Get - Get User By Id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCatById(@PathVariable("id") Long id){
        CategoryDto category = categoryService.getById(id);
        return ResponseEntity.ok(category);
    }

    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCat(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(new ApiResponse(200,"Category Deleted Succesfully",true),HttpStatus.OK);
    }
}
