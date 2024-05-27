package com.lvucko.cookshare.services;

import com.lvucko.cookshare.dao.PictureDao;
import com.lvucko.cookshare.dao.UserDao;
import com.lvucko.cookshare.dto.UserDetailsDto;
import com.lvucko.cookshare.dto.UserLoginDto;
import com.lvucko.cookshare.dto.UserRegistrationDto;
import com.lvucko.cookshare.exceptions.UserRegistrationException;
import com.lvucko.cookshare.models.Picture;
import com.lvucko.cookshare.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import com.lvucko.cookshare.mappers.UserMapper;
import com.lvucko.cookshare.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final PictureDao pictureDao;
    private final UserMapper userMapper;
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
        userDao.registerUser(userRegistrationDto);
    }

    //temporary return value
    public UserDetailsDto loginUser(UserLoginDto userLoginDto){
        User user;
        if(userLoginDto.getUserLogin().contains("@")){
            user = userDao.loginViaEmail(userLoginDto);
        }
        else user = userDao.loginViaUsername(userLoginDto);
        Picture picture = pictureDao.getPicture(user.getPictureId());
        return userMapper.mapToDetails(user, picture);
    }
}
