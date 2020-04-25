package com.epam.lab.repository.jdbc.repository.impl.mapper;


import com.epam.lab.model.entities.Author;
import com.epam.lab.model.entities.News;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.lab.statics.data.holder.ConstantFieldsHolder.*;


public class NewsMapper implements RowMapper<News> {
    //to do mapper
    @Override
    public News mapRow(ResultSet resultSet, int i) throws SQLException {
        News obj = new News();
        Author author = new Author();
        int authorId = resultSet.getInt(AUTHOR_ID);
        String name = resultSet.getString(NAME);
        String surname = resultSet.getString(SURNAME);
        int id = resultSet.getInt(ID);
        String title = resultSet.getString(TITLE);
        String shortText = resultSet.getString(SHORT_TEXT);
        String fullText = resultSet.getString(FULL_TEXT);
        String creDate = resultSet.getString(CRE_DATE);
        String modDate = resultSet.getString(MOD_DATE);

        author.setId(authorId);
        author.setName(name);
        author.setSurname(surname);
        obj.setId(id);
        obj.setTitle(title);
        obj.setShortText(shortText);
        obj.setFullText(fullText);
        obj.setCreationDate(creDate);
        obj.setAuthor(author);
        obj.setModificationDate(modDate);
        return obj;
    }
}
