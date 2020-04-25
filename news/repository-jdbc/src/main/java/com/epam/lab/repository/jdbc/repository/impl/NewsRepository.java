package com.epam.lab.repository.jdbc.repository.impl;


import com.epam.lab.model.entities.Author;
import com.epam.lab.model.entities.News;
import com.epam.lab.repository.jdbc.repository.AbstractRepository;
import com.epam.lab.repository.jdbc.repository.impl.mapper.NewsMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.epam.lab.statics.data.holder.ConstantFieldsHolder.*;
import static com.epam.lab.statics.data.query.NewsConstantQuery.*;

@Repository
@Profile("jdbc")
public class NewsRepository extends AbstractRepository<News> {

    @Override
    public Optional<News> findById(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource(ID, id);
        List<News> list = jdbcTemplate.query(SELECT_NEWS_BY_ID, params, new NewsMapper());
        return list.stream().findFirst();
    }

    @Override
    public List<News> findAll() {
        return jdbcTemplate.query(SELECT_ALL_NEWS, new NewsMapper());
    }

    @Override
    public News save(News entity) {
        SqlParameterSource params = getParams(entity);
        jdbcTemplate.update(INSERT_INTO_NEWS, params);
        return entity;
    }

    @Override
    public List<News> saveAll(List<News> entity) {
        SqlParameterSource[] source = getParamsList(entity);
        jdbcTemplate.batchUpdate(INSERT_INTO_NEWS, source);
        return entity;

    }

    @Override
    public void deleteById(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource(ID, id);
        jdbcTemplate.update(DELETE_NEWS_BY_ID, params);
    }

    @Override
    public News update(News entity) {
        SqlParameterSource params = getParams(entity);
        jdbcTemplate.update(UPDATE_NEWS, params);
        return entity;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcOperations().execute(DELETE_ALL);
    }

    public Integer selectCount() {
        return jdbcTemplate.getJdbcOperations().queryForObject(COUNT, new Object[]{}, Integer.class);
    }

    @Override
    protected SqlParameterSource getParams(News entity) {
        Author author = entity.getAuthor();
        int id = (entity.getId() != 0) ? entity.getId() : entity.hashCode();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate creation = LocalDate.parse(entity.getCreationDate(), formatter);
        LocalDate modification = LocalDate.parse(entity.getModificationDate(), formatter);
        entity.setId(id);
        author.setId(author.hashCode());
        return new MapSqlParameterSource()
                .addValue(ID, id)
                .addValue(AUTHOR_ID, author.getId())
                .addValue(NAME, author.getName())
                .addValue(SURNAME, author.getSurname())
                .addValue(TITLE, entity.getTitle())
                .addValue(SHORT_TEXT, entity.getShortText())
                .addValue(FULL_TEXT, entity.getFullText())
                .addValue(CRE_DATE, creation)
                .addValue(MOD_DATE, modification);
    }

    @Override
    protected SqlParameterSource[] getParamsList(List<News> entity) {
        SqlParameterSource[] paramsList = new SqlParameterSource[entity.size()];
        int i = 0;
        for (News n : entity) {
            paramsList[i++] = getParams(n);
        }
        return paramsList;
    }

}
