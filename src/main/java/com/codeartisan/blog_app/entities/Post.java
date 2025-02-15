package com.codeartisan.blog_app.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "post_title", length = 100, nullable = false)
    private String title;

    @Column(name = "post_content",length = 1000)
    private String content;

    @Column(name = "post_imageName")
    private String imageName;

    @Column(name = "post_createdDate")
    private Date createdDate;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Comment> comments= new ArrayList<>();

}
