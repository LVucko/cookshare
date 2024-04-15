package com.lvucko.cookshare.mappers.rowMappers;

import com.lvucko.cookshare.models.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        try{
            return User.builder()
                    .id(resultSet.getLong("id"))
                    .email(resultSet.getString("email"))
                    .username(resultSet.getString("username"))
                    .password(resultSet.getString("password"))
                    .creationDate(resultSet.getDate("creationDate"))
                    .realName(resultSet.getString("realName"))
                    .phone(resultSet.getString("phone"))
                    .pathToPicture(resultSet.getString("pathToPicture"))
                    .build();
        } catch (SQLException exception){
            return null;
        }

    }
}
