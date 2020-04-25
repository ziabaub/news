package com.epam.lab.repository.jdbc.repository.impl;


import com.epam.lab.model.entities.User;
import com.epam.lab.repository.jdbc.repository.AbstractRepository;
import com.epam.lab.repository.jdbc.repository.impl.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.epam.lab.statics.data.holder.ConstantFieldsHolder.*;
import static com.epam.lab.statics.data.query.UserConstantQuery.*;


@Repository
@Profile("jdbc")
public class UserRepository extends AbstractRepository<User> {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserRepository(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findById(int id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource(ROLE_ID, id);
        List<User> list = jdbcTemplate.query(SELECT_USER_BY_ID, paramSource, new UserMapper());
        return list.stream().findFirst();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SELECT_ALL_USER, new UserMapper());
    }

    @Override
    public User save(User entity) {
        SqlParameterSource params = getParams(entity);
        jdbcTemplate.update(INSERT_INTO_USER, params);
        return entity;
    }

    @Override
    public List<User> saveAll(List<User> entity) {
        SqlParameterSource[] source = getParamsList(entity);
        jdbcTemplate.batchUpdate(INSERT_INTO_USER, source);
        return entity;
    }

    @Override
    public void deleteById(int id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource(ROLE_ID, id);
        jdbcTemplate.update(DELETE_USER_BY_ID, paramSource);

    }

    @Override
    public User update(User entity) {
        SqlParameterSource params = getParams(entity);
        jdbcTemplate.update(UPDATE_USER, params);
        return entity;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcOperations().execute(DELETE_ALL);
    }

    @Override
    protected SqlParameterSource getParams(User entity) {
        int id = (entity.getId() != 0) ? entity.getId() : entity.hashCode();
        entity.setId(id);
        entity.getRoles().setId(id);
        return new MapSqlParameterSource()
                .addValue(ROLE_ID, id)
                .addValue(NAME, entity.getName())
                .addValue(SURNAME, entity.getSurname())
                .addValue(LOGIN, entity.getLogin())
                .addValue(ROLE, entity.getRoles().getRole())
                .addValue(PASS, passwordEncoder.encode(entity.getPassword()));
    }

    @Override
    protected SqlParameterSource[] getParamsList(List<User> entity) {
        SqlParameterSource[] paramsList = new SqlParameterSource[entity.size()];
        int i = 0;
        for (User u : entity) {
            paramsList[i++] = getParams(u);
        }
        return paramsList;
    }

    @Override
    public User findBy(String login) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource(LOGIN, login);
        return jdbcTemplate.queryForObject(SELECT_USER_BY_LOGIN, paramSource, new UserMapper());
    }
}
