package com.codeartisan.blog_app.services.impl;

import com.codeartisan.blog_app.configs.AppConstants;
import com.codeartisan.blog_app.entities.Role;
import com.codeartisan.blog_app.entities.User;
import com.codeartisan.blog_app.exceptions.ResourceNotFoundException;
import com.codeartisan.blog_app.models.UserDto;
import com.codeartisan.blog_app.repositories.RoleRepo;
import com.codeartisan.blog_app.repositories.UserRepo;
import com.codeartisan.blog_app.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepo roleRepo;

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = dtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = dtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepo.findById(AppConstants.EXECUTIVE_ROLE).orElseThrow(() -> new ResourceNotFoundException("role", "id", AppConstants.EXECUTIVE_ROLE));
        user.getRoles().add(role);
        User savedUser = userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto getById(Long id) {
        User getUser = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
        return this.userToDto(getUser);

    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        User getUser = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
        getUser.setName(userDto.getName());
        getUser.setPhone(userDto.getPhone());
        getUser.setEmail(userDto.getEmail());
        getUser.setAbout(userDto.getAbout());
//        getUser.setPassword(userDto.getPassword());
        getUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User updatedUser = userRepo.save(getUser);
        return userToDto(updatedUser);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        userRepo.delete(user);
    }

    private User dtoToUser(UserDto dto) {

//        User user = new User();
//        user.setName(dto.getName());
//        user.setPhone(dto.getPhone());
//        user.setEmail(dto.getEmail());
//        user.setAbout(dto.getAbout());
//        user.setPassword(dto.getPassword());
        User user = modelMapper.map(dto, User.class);

        return user;
    }

    private UserDto userToDto(User user) {
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setPhone(user.getPhone());
//        userDto.setEmail(user.getEmail());
//        userDto.setAbout(user.getAbout());
//        userDto.setPassword(user.getPassword());
        UserDto userDto = modelMapper.map(user, UserDto.class);


        return userDto;
    }
}
