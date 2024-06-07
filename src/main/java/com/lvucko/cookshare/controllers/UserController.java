package com.lvucko.cookshare.controllers;

import com.lvucko.cookshare.dto.UserDetailsDto;
import com.lvucko.cookshare.dto.UserLoginDto;
import com.lvucko.cookshare.dto.UserRegistrationDto;
import com.lvucko.cookshare.models.Comment;
import com.lvucko.cookshare.models.User;
import com.lvucko.cookshare.security.JwtService;
import com.lvucko.cookshare.security.configs.LoginResponse;
import com.lvucko.cookshare.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lvucko.cookshare.services.UserService;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;
    private final CommentService commentService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<UserDetailsDto>> getUsers() throws SQLException {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> getUser(@PathVariable("id") Long userId) throws SQLException {
        return ResponseEntity.ok(userService.getUserById(userId));
    }
    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registerUser(@RequestBody UserRegistrationDto user) throws SQLException{
        userService.registerUser(user);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody UserLoginDto user) throws SQLException{
        return ResponseEntity.ok(userService.loginUser(user));
    }

    @GetMapping("/{id}/comments/")
    public ResponseEntity<List<Comment>> getUserComments(@PathVariable("id") Long userId){
        return ResponseEntity.ok(commentService.getAllUserComment(userId));
    }
    @DeleteMapping("/{id}/comments")
    public ResponseEntity<HttpStatus> deleteAllCommentsFromUser(@PathVariable("id") long userId){
        commentService.deleteAllCommentsFromUser(userId);
        return ResponseEntity.ok().build();
    }
}
