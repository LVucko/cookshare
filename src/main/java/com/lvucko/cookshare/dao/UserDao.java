package com.lvucko.cookshare.dao;

import com.lvucko.cookshare.dto.UserLoginDto;
import com.lvucko.cookshare.dto.UserRegistrationDto;
import lombok.RequiredArgsConstructor;
import com.lvucko.cookshare.mappers.rowMappers.UserRowMapper;
import com.lvucko.cookshare.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    public List<User> getAllUsers(){
        String sql = """
                     SELECT * FROM users
                     """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));

    }
    public User getUserById(long userId){
        String sql = """
                     SELECT * FROM users WHERE users.id = ?
                     """;
        return jdbcTemplate.queryForObject(sql, userRowMapper, userId);
    }
    public User getUserByEmail(String email){
        String sql = """
                     SELECT * FROM users WHERE users.email = ?
                     """;
        return jdbcTemplate.queryForObject(sql, userRowMapper, email);
    }
    public User getUserByUsername(String username){
        String sql = """
                     SELECT * FROM users WHERE users.username = ?
                     """;
        return jdbcTemplate.queryForObject(sql, userRowMapper, username);
    }
    public boolean isUsernameTaken(String username){
        String sql = """
                    SELECT EXISTS(SELECT * FROM users WHERE users.username = ?)
                    """;
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, username));
    }
    public boolean isEmailTaken(String email){
        String sql = """
                    SELECT EXISTS(SELECT * FROM users WHERE users.email = ?)
                    """;
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, email));
    }

    public void registerUser(UserRegistrationDto user){
        String sql = """
                    INSERT INTO users (username, email, password, realName, creationDate, phone, pictureid)
                    VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, ?, ?);
                    """;
        jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getPassword(), user.getRealName(), user.getPhone(), user.getPictureId());
    }
    public User loginViaUsername(UserLoginDto userLoginDto){
        String sql = """
                  SELECT * FROM users WHERE users.username = ? AND password = ?
                  """;
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class),  userLoginDto.getUserLogin(), userLoginDto.getPassword());
    }
    public User loginViaEmail(UserLoginDto userLoginDto){
        String sql = """
                  SELECT * FROM users WHERE users.email = ? AND password = ?
                  """;
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class),  userLoginDto.getUserLogin(), userLoginDto.getPassword());
    }
}
