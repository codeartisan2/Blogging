package com.codeartisan.blog_app.controllers;

import com.codeartisan.blog_app.models.JwtAuthRequest;
import com.codeartisan.blog_app.models.JwtAuthResponse;
import com.codeartisan.blog_app.models.UserDto;
import com.codeartisan.blog_app.security.JwtTokenHelper;
import com.codeartisan.blog_app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody JwtAuthRequest request) {
        System.out.println("Login endpoint hit");
//        try {
        authenticate(request.getUsername(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenHelper.generateToken(userDetails);
        System.out.println(token);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace(); // Print error stack trace
//
//            JwtAuthResponse errorResp = new JwtAuthResponse();
//            errorResp.setToken("Error generating token");
//            return new ResponseEntity<>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    @PostMapping("register")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto savedUserDto = userService.registerUser(userDto);
        return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
    }

    public void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            authenticationManager.authenticate(authenticationToken);

        } catch (BadCredentialsException e) {
            System.out.println("Authentication failed for user: " + username);
            throw e;
        }

    }
}
