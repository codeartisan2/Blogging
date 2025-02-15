package com.codeartisan.blog_app.repositories;

import com.codeartisan.blog_app.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Long> {
}
