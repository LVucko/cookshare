package com.lvucko.cookshare.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lvucko.cookshare.dao.UserDao;
import com.lvucko.cookshare.dto.UserDetailsDto;
import com.lvucko.cookshare.dto.UserLoginDto;
import com.lvucko.cookshare.dto.UserRegistrationDto;
import lombok.RequiredArgsConstructor;
import com.lvucko.cookshare.mappers.UserMapper;
import com.lvucko.cookshare.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final UserMapper userMapper;
    public List<UserDetailsDto> getAllUsers(){
        List<User> users = userDao.getAllUsers();
        List<UserDetailsDto> userDetailsDtos = new ArrayList<>();
        users.forEach(user ->userDetailsDtos.add(userMapper.mapToDetails(user)));
        return  userDetailsDtos;
    }
    public UserDetailsDto getUserById(long id){
        User user = userDao.getUserById(id);
        return userMapper.mapToDetails(user);
    }
    public UserDetailsDto getUserByEmail(String email){
        User user = userDao.getUserByEmail(email);
        return userMapper.mapToDetails(user);
    }
    public UserDetailsDto getUserByUsername(String username){
        User user = userDao.getUserByUsername(username);
        return userMapper.mapToDetails(user);
    }
    public int registerUser(UserRegistrationDto userRegistrationDto){
        if(!userDao.isUsernameTaken(userRegistrationDto.getUsername())){
            if(!userDao.isEmailTaken(userRegistrationDto.getEmail())) {
                boolean status = userDao.registerUser(userRegistrationDto);
                if(status){
                    return 1;
                }
            }
        }
        return 0;
    }
    //temporary return value
    public UserDetailsDto loginUser(UserLoginDto userLoginDto){
        User user;
        if(userLoginDto.getUserLogin().contains("@")){
            user = userDao.loginViaEmail(userLoginDto);
        }
        else user = userDao.loginViaUsername(userLoginDto);
        return userMapper.mapToDetails(user);
    }
}
