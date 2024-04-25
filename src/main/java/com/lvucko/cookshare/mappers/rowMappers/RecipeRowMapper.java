package com.lvucko.cookshare.mappers.rowMappers;

import com.lvucko.cookshare.models.Recipe;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RecipeRowMapper implements RowMapper<Recipe> {
    @Override
    public Recipe mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        try {
            return Recipe.builder()
                    .id(resultSet.getLong("id"))
                    .userId(resultSet.getLong("userId"))
                    .creationDate(resultSet.getDate("creationDate"))
                    .title(resultSet.getString("title"))
                    .shortDescription(resultSet.getString("shortDescription"))
                    .longDescription(resultSet.getString("longDescription"))
                    .build();
        } catch (SQLException exception) {
            return null;
        }
    }
}
