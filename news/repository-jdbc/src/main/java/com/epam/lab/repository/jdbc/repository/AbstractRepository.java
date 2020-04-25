package com.epam.lab.repository.jdbc.repository;


import com.epam.lab.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

public abstract class AbstractRepository<T> implements Repository<T> {

    @Override
    public T findBy(String s) {
        return null;
    }

    protected NamedParameterJdbcTemplate jdbcTemplate;

    protected abstract SqlParameterSource getParams(T entity);

    protected abstract SqlParameterSource[] getParamsList(List<T> entity);

    @Autowired
    @Profile("jdbc")
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
