package com.lvucko.cookshare.controllers;

import com.lvucko.cookshare.dto.*;
import com.lvucko.cookshare.models.Comment;
import com.lvucko.cookshare.models.User;
import com.lvucko.cookshare.security.JwtService;
import com.lvucko.cookshare.security.configs.LoginResponse;
import com.lvucko.cookshare.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@RequestHeader HttpHeaders headers, @RequestBody UserUpdateDto updatedUser) throws SQLException{
        Long userId = jwtService.extractClaim(jwtService.extractTokenFromHeaders(headers), claims -> claims.get("UserId", Long.class));
        userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> getUser(@PathVariable("id") Long userId) throws SQLException {
        return ResponseEntity.ok(userService.getUserById(userId));
    }
    @GetMapping("/personal/{id}")
    public ResponseEntity<UserPersonalDetailsDto> getUserPersonalDetails(@RequestHeader HttpHeaders headers, @PathVariable("id") Long userId) throws SQLException {
        Long tokenUserId = jwtService.extractClaim(jwtService.extractTokenFromHeaders(headers), claims -> claims.get("UserId", Long.class));
        return ResponseEntity.ok(userService.getUserPersonalDetails(tokenUserId, userId));
    }
    @GetMapping("/personal/username/{username}")
    public ResponseEntity<UserPersonalDetailsDto> getUserPersonalDetailsByUsername(@PathVariable("username") String username) throws SQLException {
        return ResponseEntity.ok(userService.getUserPersonalDetailsByUsername(username));
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

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getUserComments(@PathVariable("id") Long userId){
        return ResponseEntity.ok(commentService.getAllUserComment(userId));
    }
    @DeleteMapping("/{id}/comments")
    public ResponseEntity<HttpStatus> deleteAllCommentsFromUser(@PathVariable("id") long userId){
        commentService.deleteAllCommentsFromUser(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/role/{role}")
    public ResponseEntity<HttpStatus> updateUserRole(@PathVariable("id") long userId, @PathVariable("role") String role){
        userService.updateUserRole(userId, role);
        return ResponseEntity.ok().build();
    }
}
