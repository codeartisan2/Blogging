package com.codeartisan.blog_app.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
private Long id;

@NotEmpty
@Size(min = 4, message = "Title must contain atleast 4 character")
private String categoryTitle;

@NotEmpty
@Size(min = 10, message = "Description must contain atlest 10 character")
private String categoryDescription;

}
