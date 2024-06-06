package com.lvucko.cookshare.security.configs;

import com.lvucko.cookshare.dao.UserDao;
import com.lvucko.cookshare.exceptions.UserNotFoundException;
import com.lvucko.cookshare.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    private final UserDao userDao;
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            User user;
            if(username.contains("@")){
                try{user = userDao.getUserByEmail(username);
                }
                catch(EmptyResultDataAccessException e){
                    throw new UserNotFoundException("Unable to find user x1 ");
                }
            }
            else{
                try
                {user = userDao.getUserByUsername(username);
                }
                catch(EmptyResultDataAccessException e){
                    throw new UserNotFoundException("Unable to find user x2 ");
                }
            }
            return user;
        };
    }
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
