package com.codeartisan.blog_app.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String imageName;
    private Date createdDate;
    private CategoryDto category;
    private UserDto user;
    private List<CommentDto> comments= new ArrayList<>();
}
