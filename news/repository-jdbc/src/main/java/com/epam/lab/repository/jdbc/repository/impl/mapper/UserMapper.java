package com.epam.lab.repository.jdbc.repository.impl.mapper;


import com.epam.lab.model.entities.Roles;
import com.epam.lab.model.entities.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.lab.statics.data.holder.ConstantFieldsHolder.*;


public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User obj = new User();
        Roles role = new Roles();
        String roleName = resultSet.getString(ROLE);
        int id = resultSet.getInt(ROLE_ID);
        String name = resultSet.getString(NAME);
        String surname = resultSet.getString(SURNAME);
        String login = resultSet.getString(LOGIN);
        String pass = resultSet.getString(PASS);
        role.setRole(roleName);
        role.setId(id);
        obj.setId(id);
        obj.setName(name);
        obj.setSurname(surname);
        obj.setLogin(login);
        obj.setPassword(pass);
        obj.setRoles(role);
        return obj;
    }
}
