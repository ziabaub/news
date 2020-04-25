package com.epam.lab.repository.jdbc.repository.impl.mapper;


import com.epam.lab.model.entities.Roles;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.lab.statics.data.holder.ConstantFieldsHolder.ID;
import static com.epam.lab.statics.data.holder.ConstantFieldsHolder.ROLE;


public class RoleMapper implements RowMapper<Roles> {

    @Override
    public Roles mapRow(ResultSet resultSet, int i) throws SQLException {
        Roles obj = new Roles();
        int id = resultSet.getInt(ID);
        String role = resultSet.getString(ROLE);
        obj.setId(id);
        obj.setRole(role);
        return obj;
    }
}
