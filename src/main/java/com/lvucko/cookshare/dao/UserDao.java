package com.lvucko.cookshare.dao;

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
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }
}
