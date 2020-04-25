package com.epam.lab.repository.jdbc.repository.impl;


import com.epam.lab.model.entities.Author;
import com.epam.lab.repository.jdbc.repository.AbstractRepository;
import com.epam.lab.repository.jdbc.repository.impl.mapper.AuthorMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.epam.lab.statics.data.holder.ConstantFieldsHolder.*;
import static com.epam.lab.statics.data.query.AuthorConstantQuery.*;


@Repository
@Profile("jdbc")
public class AuthorRepository extends AbstractRepository<Author> {

    @Override
    public Optional<Author> findById(int id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource(ID, id);
        List<Author> list = jdbcTemplate.query(SELECT_AUTHOR_BY_ID, paramSource, new AuthorMapper());
        return list.stream().findFirst();
    }

    @Override
    public List<Author> findAll() {
        return jdbcTemplate.query(SELECT_ALL_AUTHOR, new AuthorMapper());
    }

    @Override
    public Author save(Author entity) {
        SqlParameterSource params = getParams(entity);
        jdbcTemplate.update(INSERT_INTO_AUTHOR, params);
        return entity;
    }

    @Override
    public List<Author> saveAll(List<Author> entity) {
        SqlParameterSource[] source = getParamsList(entity);
        jdbcTemplate.batchUpdate(INSERT_INTO_AUTHOR, source);
        return entity;
    }

    @Override
    public void deleteById(int id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource(ID, id);
        jdbcTemplate.update(DELETE_AUTHOR_BY_ID, paramSource);
    }

    @Override
    public Author update(Author entity) {
        SqlParameterSource params = getParams(entity);
        jdbcTemplate.update(UPDATE_AUTHOR, params);
        return entity;
    }


    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcOperations().execute(DELETE_ALL);
    }

    @Override
    protected SqlParameterSource getParams(Author entity) {
        int id = (entity.getId() != 0) ? entity.getId() : entity.hashCode();
        entity.setId(id);
        return new MapSqlParameterSource()
                .addValue(ID, id)
                .addValue(NAME, entity.getName())
                .addValue(SURNAME, entity.getSurname());
    }

    @Override
    protected SqlParameterSource[] getParamsList(List<Author> entity) {
        SqlParameterSource[] paramsList = new SqlParameterSource[entity.size()];
        int i = 0;
        for (Author a : entity) {
            paramsList[i++] = getParams(a);
        }
        return paramsList;
    }
}
