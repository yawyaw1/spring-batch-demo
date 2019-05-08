package com.spring.batch.example.mapper;

import com.spring.batch.example.entities.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Adservio on 04/05/2019.
 */

public class CustomRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFistname(resultSet.getString("fistname"));
        user.setLastname(resultSet.getString("lastname"));
        user.setUsername(resultSet.getString("username"));
        return user;
    }
}
