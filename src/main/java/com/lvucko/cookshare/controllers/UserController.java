package com.lvucko.cookshare.controllers;


import com.lvucko.cookshare.dto.UserDetailsDto;
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

    @GetMapping
    public ResponseEntity<List<UserDetailsDto>> getUsers()
        throws SQLException {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> getUser(@PathVariable("id") Long userId)
        throws SQLException {
        return ResponseEntity.ok(userService.getUserById(userId));

    }
}
