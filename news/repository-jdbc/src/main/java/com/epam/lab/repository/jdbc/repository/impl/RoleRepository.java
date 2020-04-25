package com.epam.lab.repository.jdbc.repository.impl;


import com.epam.lab.model.entities.Roles;
import com.epam.lab.repository.jdbc.repository.AbstractRepository;
import com.epam.lab.repository.jdbc.repository.impl.mapper.RoleMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.epam.lab.statics.data.holder.ConstantFieldsHolder.ID;
import static com.epam.lab.statics.data.holder.ConstantFieldsHolder.ROLE;
import static com.epam.lab.statics.data.query.RoleConstantQuery.*;


@Repository
@Profile("jdbc")
public class RoleRepository extends AbstractRepository<Roles> {

    @Override
    public Optional<Roles> findById(int id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource(ID, id);
        List<Roles> list = jdbcTemplate.query(SELECT_ROLE_BY_ID, paramSource, new RoleMapper());
        return list.stream().findFirst();
    }

    @Override
    public List<Roles> findAll() {
        return jdbcTemplate.query(SELECT_ALL_ROLE, new RoleMapper());
    }

    @Override
    public Roles save(Roles entity) {
        SqlParameterSource params = getParams(entity);
        jdbcTemplate.update(INSERT_INTO_ROLE, params);
        return entity;
    }

    @Override
    public List<Roles> saveAll(List<Roles> entity) {
        SqlParameterSource[] source = getParamsList(entity);
        jdbcTemplate.batchUpdate(INSERT_INTO_ROLE, source);
        return entity;
    }

    @Override
    public void deleteById(int id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource(ID, id);
        jdbcTemplate.update(DELETE_ROLE_BY_ID, paramSource);
    }

    @Override
    public Roles update(Roles entity) {
        SqlParameterSource params = getParams(entity);
        jdbcTemplate.update(UPDATE_ROLE, params);
        return entity;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcOperations().execute(DELETE_ALL);
    }

    @Override
    protected SqlParameterSource getParams(Roles entity) {
        int id = (entity.getId() != 0) ? entity.getId() : entity.hashCode();
        entity.setId(id);
        return new MapSqlParameterSource()
                .addValue(ID, id)
                .addValue(ROLE, entity.getRole());
    }

    @Override
    protected SqlParameterSource[] getParamsList(List<Roles> entity) {
        SqlParameterSource[] paramsList = new SqlParameterSource[entity.size()];
        int i = 0;
        for (Roles r : entity) {
            paramsList[i++] = getParams(r);
        }
        return paramsList;
    }
}
