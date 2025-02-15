package com.codeartisan.blog_app.controllers;

import com.codeartisan.blog_app.models.ApiResponse;
import com.codeartisan.blog_app.models.UserDto;
import com.codeartisan.blog_app.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "User API for authentication and management")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Post - Create User
    @PostMapping("/")
    @Operation(summary = "Create a new user", description = "Registers a new user in the system")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

        UserDto savedUserDto = userService.createUser(userDto);
        return new ResponseEntity<>(savedUserDto,HttpStatus.CREATED);
    }

    // Put - Update User
    @PutMapping("/{id}")
    @Operation(summary = "Update existing user", description = "Update user in the system")

    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("id") Long id) {
        UserDto updatedUserDto = userService.updateUser(userDto, id);
        return ResponseEntity.ok(updatedUserDto);
    }
    // Get - Get All User
    @GetMapping("/")
    @Operation(summary = "Get All Users", description = "Fetches all users from system")

    public  ResponseEntity<List<UserDto>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    // Get - Get User By Id
    @GetMapping("/{id}")
    @Operation(summary = "Get User by ID", description = "Fetches user details using the user ID")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id){
        UserDto user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(new ApiResponse(200,"User Deleted Succesfully",true),HttpStatus.OK);
    }
}
