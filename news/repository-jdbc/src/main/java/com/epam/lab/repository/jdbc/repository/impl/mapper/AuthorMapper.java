package com.epam.lab.repository.jdbc.repository.impl.mapper;

import com.epam.lab.model.entities.Author;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.lab.statics.data.holder.ConstantFieldsHolder.*;


public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
        Author obj = new Author();
        int id = resultSet.getInt(ID);
        String name = resultSet.getString(NAME);
        String surname = resultSet.getString(SURNAME);
        obj.setId(id);
        obj.setName(name);
        obj.setSurname(surname);
        return obj;
    }

}
