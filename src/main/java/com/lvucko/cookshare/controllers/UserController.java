package com.lvucko.cookshare.controllers;

import com.lvucko.cookshare.dto.UserDetailsDto;
import com.lvucko.cookshare.dto.UserLoginDto;
import com.lvucko.cookshare.dto.UserRegistrationDto;
import com.lvucko.cookshare.validators.UserValidator;
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
    @GetMapping("/all")
    public ResponseEntity<List<UserDetailsDto>> getUsers() throws SQLException {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> getUser(@PathVariable("id") Long userId) throws SQLException {
        return ResponseEntity.ok(userService.getUserById(userId));
    }
    @GetMapping("/{username}")
    public ResponseEntity<UserDetailsDto> getUser(@PathVariable("username") String username) throws SQLException {
        if (username.contains("@")){
            return ResponseEntity.ok(userService.getUserByEmail(username));
        }
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto user) throws SQLException{
        final UserValidator userValidator;
        if(!UserValidator.isValidUsername(user.getUsername())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username is not valid");
        }
        if(!UserValidator.isValidEmail(user.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email is not valid");
        }
        if(!UserValidator.isValidPassword(user.getPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("password is not valid");
        }
        int status = userService.registerUser(user);
        if(status == 1){
            return ResponseEntity.status(HttpStatus.OK).body("everything is ok");
        }
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username/email taken");
    }
    @PostMapping("/login")
    public ResponseEntity<UserDetailsDto> loginUser(@RequestBody UserLoginDto user) throws SQLException{
        return ResponseEntity.ok(userService.loginUser(user));
    }
}
