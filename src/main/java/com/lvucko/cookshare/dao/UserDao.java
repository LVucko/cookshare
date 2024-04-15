package com.lvucko.cookshare.dao;

import com.lvucko.cookshare.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import com.lvucko.cookshare.mappers.rowMappers.UserRowMapper;
import com.lvucko.cookshare.models.User;
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
        return jdbcTemplate.query(sql, userRowMapper);
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
}
