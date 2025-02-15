package com.codeartisan.blog_app.services.impl;
import com.codeartisan.blog_app.entities.Category;
import com.codeartisan.blog_app.exceptions.ResourceNotFoundException;
import com.codeartisan.blog_app.models.CategoryDto;
import com.codeartisan.blog_app.repositories.CategoryRepo;
import com.codeartisan.blog_app.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto,Category.class);
        Category savedCatgory = categoryRepo.save(category);
        return modelMapper.map(savedCatgory,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        Category category  = categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("category", "id", id));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCatgory = categoryRepo.save(category);
        return modelMapper.map(updatedCatgory,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
       List<Category> categories = categoryRepo.findAll();
      List<CategoryDto> categoryDtos = categories.stream().map(category -> modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
      return categoryDtos;
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category  = categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("category", "id", id));
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("category", "Id", id));
        categoryRepo.delete(category);
    }
}
