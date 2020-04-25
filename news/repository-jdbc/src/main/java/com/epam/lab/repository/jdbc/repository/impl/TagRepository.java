package com.epam.lab.repository.jdbc.repository.impl;


import com.epam.lab.model.entities.Tag;
import com.epam.lab.repository.jdbc.repository.AbstractRepository;
import com.epam.lab.repository.jdbc.repository.impl.mapper.TagMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.epam.lab.statics.data.holder.ConstantFieldsHolder.ID;
import static com.epam.lab.statics.data.holder.ConstantFieldsHolder.NAME;
import static com.epam.lab.statics.data.query.TagConstantQuery.*;


@Repository
@Profile("jdbc")
public class TagRepository extends AbstractRepository<Tag> {

    @Override
    public Optional<Tag> findById(int id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource(ID, id);
        List<Tag> list = jdbcTemplate.query(SELECT_TAG_BY_ID, paramSource, new TagMapper());
        return list.stream().findFirst();
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SELECT_ALL_TAG, new TagMapper());
    }

    @Override
    public Tag save(Tag entity) {
        SqlParameterSource params = getParams(entity);
        jdbcTemplate.update(INSERT_INTO_TAG, params);
        return entity;
    }

    @Override
    public List<Tag> saveAll(List<Tag> entity) {
        SqlParameterSource[] source = getParamsList(entity);
        jdbcTemplate.batchUpdate(INSERT_INTO_TAG, source);
        return entity;
    }

    @Override
    public void deleteById(int id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource(ID, id);
        jdbcTemplate.update(DELETE_TAG_BY_ID, paramSource);

    }

    @Override
    public Tag update(Tag entity) {
        SqlParameterSource params = getParams(entity);
        jdbcTemplate.update(UPDATE_TAG, params);
        return entity;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcOperations().execute(DELETE_ALL);

    }

    @Override
    protected SqlParameterSource getParams(Tag entity) {
        int id = (entity.getId() != 0) ? entity.getId() : entity.hashCode();
        entity.setId(id);
        return new MapSqlParameterSource()
                .addValue(ID, id)
                .addValue(NAME, entity.getName());
    }

    @Override
    protected SqlParameterSource[] getParamsList(List<Tag> entity) {
        SqlParameterSource[] paramsList = new SqlParameterSource[entity.size()];
        int i = 0;
        for (Tag t : entity) {
            paramsList[i++] = getParams(t);
        }
        return paramsList;
    }
}
