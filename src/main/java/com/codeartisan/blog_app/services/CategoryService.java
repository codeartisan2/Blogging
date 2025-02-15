package com.codeartisan.blog_app.services;

import com.codeartisan.blog_app.models.CategoryDto;

import java.util.List;

public interface CategoryService {
    // Create Category
    CategoryDto createCategory(CategoryDto categoryDto);

    // Update Category
    CategoryDto updateCategory(CategoryDto categoryDto, Long id);

    // Get all Category
    List<CategoryDto> getAllCategory();

    CategoryDto getById(Long id);

    void deleteCategory(Long id);
}