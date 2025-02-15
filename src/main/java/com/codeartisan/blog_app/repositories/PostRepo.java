package com.codeartisan.blog_app.repositories;

import com.codeartisan.blog_app.entities.Category;
import com.codeartisan.blog_app.entities.Post;
import com.codeartisan.blog_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);
    List<Post> findByTitleContaining(String str);

}
