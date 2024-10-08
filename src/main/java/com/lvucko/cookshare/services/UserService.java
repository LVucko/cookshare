package com.lvucko.cookshare.services;

import com.lvucko.cookshare.dao.PictureDao;
import com.lvucko.cookshare.dao.UserDao;
import com.lvucko.cookshare.dto.*;
import com.lvucko.cookshare.enums.Role;
import com.lvucko.cookshare.exceptions.UnauthorizedException;
import com.lvucko.cookshare.exceptions.UserLoginException;
import com.lvucko.cookshare.exceptions.UserNotFoundException;
import com.lvucko.cookshare.exceptions.UserRegistrationException;
import com.lvucko.cookshare.models.Picture;
import com.lvucko.cookshare.security.JwtService;
import com.lvucko.cookshare.security.configs.LoginResponse;
import com.lvucko.cookshare.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import com.lvucko.cookshare.mappers.UserMapper;
import com.lvucko.cookshare.models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final PictureDao pictureDao;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public List<UserDetailsDto> getAllUsers(){
        List<User> users = userDao.getAllUsers();
        List<UserDetailsDto> userDetailsDtos = new ArrayList<>();
        for(User user : users){
            Picture picture = pictureDao.getPicture(user.getPictureId());
            userDetailsDtos.add(userMapper.mapToDetails(user, picture));
        }
        return  userDetailsDtos;
    }
    public UserDetailsDto getUserById(long id){
        User user = userDao.getUserById(id);
        Picture picture = pictureDao.getPicture(user.getPictureId());
        return userMapper.mapToDetails(user, picture);
    }
    public UserDetailsDto getUserByEmail(String email){
        User user = userDao.getUserByEmail(email);
        Picture picture = pictureDao.getPicture(user.getPictureId());
        return userMapper.mapToDetails(user, picture);
    }
    public UserDetailsDto getUserByUsername(String username){
        User user = userDao.getUserByUsername(username);
        Picture picture = pictureDao.getPicture(user.getPictureId());
        return userMapper.mapToDetails(user, picture);
    }
    public UserPersonalDetailsDto getUserPersonalDetails(Long tokenId, Long userId){
        User tokenUser = userDao.getUserById(tokenId);
        if(tokenId.equals(userId) || tokenUser.getRole() == Role.ADMIN || tokenUser.getRole() == Role.MODERATOR){
            User user = userDao.getUserById(userId);
            Picture picture = pictureDao.getPicture(user.getPictureId());
            return userMapper.mapToPersonalDetails(user, picture);
        }
        else throw new UnauthorizedException("Unauthorized to access");
    }
    public UserPersonalDetailsDto getUserPersonalDetailsByUsername(String username){
        User user = userDao.getUserByUsername(username);
        Picture picture = pictureDao.getPicture(user.getPictureId());
        return userMapper.mapToPersonalDetails(user, picture);
    }


    public void updateUserRole(Long userId, String role){
        EnumUtils.findEnumInsensitiveCase(Role.class, role);
        userDao.setUserRole(userId, role);
    }

    public void updateUser(Long tokenId, UserUpdateDto userUpdateDto){
        User user = userDao.getUserById(tokenId);

        if(user.getId().equals(userUpdateDto.getId()) || user.getRole() == Role.ADMIN || user.getRole() == Role.MODERATOR){
            if(!UserValidator.isValidEmail(userUpdateDto.getEmail())){
                throw new UserRegistrationException("Invalid email");
            }
            User beforeChangeUser = userDao.getUserById(userUpdateDto.getId());
            if(!(beforeChangeUser.getEmail().equals(userUpdateDto.getEmail()))){
                if(userDao.isEmailTaken(userUpdateDto.getEmail()))
                    throw new UserRegistrationException("Email taken");
            }
            userDao.updateUser(userUpdateDto);
            if(userUpdateDto.getPictureId() != null){
                userDao.updateUserPicture(userUpdateDto);
            }
        }
        else throw new UnauthorizedException("Unauthorized to edit");
    }

    public void registerUser(UserRegistrationDto userRegistrationDto){
        if(!UserValidator.isValidEmail(userRegistrationDto.getEmail())){
            throw new UserRegistrationException("Invalid email");
        }
        if(!UserValidator.isValidUsername(userRegistrationDto.getUsername())){
            throw new UserRegistrationException("Invalid username");
        }
        if(!UserValidator.isValidPassword(userRegistrationDto.getPassword())){
            throw new UserRegistrationException("Invalid password");
        }
        if(userDao.isUsernameTaken(userRegistrationDto.getUsername())) {
            throw new UserRegistrationException("Username taken");
        }
        if(userDao.isEmailTaken(userRegistrationDto.getEmail())) {
            throw new UserRegistrationException("Email taken");
        }
        userRegistrationDto.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        userDao.registerUser(userRegistrationDto);
    }
    public User getUserByUsernameOrEmail(String usernameOrEmail){
        User user;
        if(usernameOrEmail.contains("@")){
            try {user = userDao.getUserByEmail(usernameOrEmail);
            }
            catch(EmptyResultDataAccessException e){
                throw new UserNotFoundException("Unable to find user");
            }
        }
        else{
            try {user = userDao.getUserByUsername(usernameOrEmail);
            }
            catch(EmptyResultDataAccessException e){
                throw new UserNotFoundException("Unable to find user");
            }
        }
        return user;
    }

    public LoginResponse loginUser(UserLoginDto userLoginDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getUserLogin(),
                        userLoginDto.getPassword()
                        )
            );
        User user = getUserByUsernameOrEmail(userLoginDto.getUserLogin());
        Map<String, Object> rawMap = new HashMap<>();
        rawMap.put("UserId", user.getId());
        rawMap.put("Role", user.getRole().name());
        return LoginResponse.builder()
                .token(jwtService.generateToken(rawMap,user))
                .expiresIn(jwtService.getExpirationTime())
                .build();
    }

}
