package com.epam.lab.repository.jdbc.repository.impl.mapper;


import com.epam.lab.model.entities.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.lab.statics.data.holder.ConstantFieldsHolder.ID;
import static com.epam.lab.statics.data.holder.ConstantFieldsHolder.NAME;


public class TagMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
        Tag obj = new Tag();
        int id = resultSet.getInt(ID);
        String name = resultSet.getString(NAME);
        obj.setId(id);
        obj.setName(name);
        return obj;
    }
}
