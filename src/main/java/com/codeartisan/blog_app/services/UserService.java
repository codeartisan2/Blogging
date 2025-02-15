package com.codeartisan.blog_app.services;

import com.codeartisan.blog_app.models.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto user);
    UserDto registerUser(UserDto user);
    UserDto getById(Long Id);
    UserDto updateUser(UserDto user, Long id);
    List<UserDto> getAllUser();
    void deleteUser(Long id);

}
