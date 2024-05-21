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
    //Isto kao i u recipe controlleru, iskoristi osnovnu rutu i onda samo s @GetMapping imas rutu koja ti dohvaca sve
    @GetMapping("/all")
    public ResponseEntity<List<UserDetailsDto>> getUsers() throws SQLException {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //Nezz sta si zamislio s ova dva endpointa, jer ti je jedan po id-u a drugi po username-u, a oba vracaju isti objekt, bolje bi bilo da imas samo jedan endpoint koji ce ti vracat usera po id-u ili username-u
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> getUser(@PathVariable("id") Long userId) throws SQLException {
        return ResponseEntity.ok(userService.getUserById(userId));
    }
    @GetMapping("/u/{username}")
    public ResponseEntity<UserDetailsDto> getUser(@PathVariable("username") String username) throws SQLException {
        if (username.contains("@")){
            return ResponseEntity.ok(userService.getUserByEmail(username));
        }
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto user) throws SQLException{
        //Ovo ti ne treba, jer vec imas UserValidator klasu koja ti provjerava sve ovo, samo pozoves metodu iz te klase i dobijes rezultat
        final UserValidator userValidator;
        //Dalje sav ovaj dio bi ja prebacio u servis, i onda u njemu pozvao metode iz UserValidator klase ili imao kombinirane provjere u jednoj metodi
        //Za vracanje string u bodyu isto kao i u recipe controlleru, vratis status code i eventualno neki objekt/podatak u ovisnosti o featuru
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
        //Umjesto statusa mozes to sve odraditi s exceptionima, pa ako se desi exception znas da je doslo do greske
        //Kreiras custom exception koja nasljeduje RuntimeException i onda u servisu kad se desi greska bacis taj exception
        //A ako sve prode kak treba vratis 200 OK
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
