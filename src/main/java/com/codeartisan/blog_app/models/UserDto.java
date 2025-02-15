package com.codeartisan.blog_app.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
    private Long id;

    @NotEmpty
    @Size(min = 4, message = "UserName must be greater than 4 character")
    private String name;
    @NotEmpty
    @Size(min = 10, max = 10, message = "Phone number must be of 10 length.")
    private String phone;
    @Email(message = "Email is not valid.")
    private String email;
    @NotEmpty
    @Size(min = 3,max = 6,message = "Password length must be between 3 and 6.")
    private String password;
    @NotNull
    private String about;

    private Set<RoleDto> roles = new HashSet<>();
}
