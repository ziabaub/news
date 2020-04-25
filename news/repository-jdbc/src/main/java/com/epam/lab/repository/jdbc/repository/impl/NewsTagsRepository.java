package com.epam.lab.repository.jdbc.repository.impl;


import com.epam.lab.model.entities.Tag;
import com.epam.lab.model.entities.TagRelation;
import com.epam.lab.repository.jdbc.repository.impl.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.epam.lab.statics.data.holder.ConstantFieldsHolder.ID;
import static com.epam.lab.statics.data.holder.ConstantFieldsHolder.TAG_ID;
import static com.epam.lab.statics.data.query.NewsTagConstantQuery.INSERT_INTO_NEWS_TAGS;
import static com.epam.lab.statics.data.query.NewsTagConstantQuery.SELECT_TAG_BY_ID;

@Repository
@Profile("jdbc")
public class NewsTagsRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Tag> selectByNews(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource(ID, id);
        return jdbcTemplate.query(SELECT_TAG_BY_ID, params, new TagMapper());

    }

    public void insert(int newsId, Tag entity) {
        SqlParameterSource params = getParams(newsId, entity);
        jdbcTemplate.update(INSERT_INTO_NEWS_TAGS, params);
    }

    public void insertAll(TagRelation tagRelation) {
        SqlParameterSource[] source = getParamsList(tagRelation);
        jdbcTemplate.batchUpdate(INSERT_INTO_NEWS_TAGS, source);
    }

    protected SqlParameterSource getParams(int newsId, Tag entity) {
        return new MapSqlParameterSource()
                .addValue(ID, newsId)
                .addValue(TAG_ID, entity.getId());
    }


    protected SqlParameterSource[] getParamsList(TagRelation tagRelation) {
        int size = tagRelation.getTags().size();
        int newsId = tagRelation.getId();
        List<Tag> tags = tagRelation.getTags();
        SqlParameterSource[] paramsList = new SqlParameterSource[size];

        int i = 0;
        for (Tag t : tags) {
            paramsList[i++] = getParams(newsId, t);
        }
        return paramsList;
    }
}
