package com.codeartisan.blog_app.repositories;

import com.codeartisan.blog_app.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {
}
