package com.epam.lab.statics.data.query;

public class NewsTagConstantQuery {
    public static final String INSERT_INTO_NEWS_TAGS =
            "INSERT INTO news_tag\n" +
                    "(news_id,tag_id)\n" +
                    "VALUES\n" +
                    "(:id,:tag_id)\n" +
                    "ON CONFLICT DO NOTHING";
    public static final String SELECT_TAG_BY_ID =
            "select\n" +
                    "nt.tag_id as id,\n" +
                    "t.name\n" +
                    "from tag as t\n" +
                    "inner join news_tag as nt on nt.tag_id = t.id\n" +
                    "where nt.news_id = :id";

    private NewsTagConstantQuery() {
    }
}