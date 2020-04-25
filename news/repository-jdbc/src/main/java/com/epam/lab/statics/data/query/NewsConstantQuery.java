package com.epam.lab.statics.data.query;

public class NewsConstantQuery {
    public static final String DELETE_ALL = "TRUNCATE news RESTART IDENTITY CASCADE";
    public static final String DELETE_NEWS_BY_ID =
            "DELETE FROM news\n" +
                    "WHERE id = :id";
    public static final String SELECT_ALL_NEWS =
            "with news_table as (\n" +
                    "SELECT\n" +
                    "n.id," +
                    "n.author_id," +
                    "title," +
                    "shorttext," +
                    "fulltext," +
                    "creationdate," +
                    "modificationdate," +
                    "name," +
                    "surname\n" +
                    "FROM news as n\n" +
                    "inner join author as a on a.id = n.author_id\n" +
                    "order by n.creationdate)\n" +
                    "select * from news_table\n";
    public static final String SELECT_NEWS_BY_ID =
            SELECT_ALL_NEWS +
                    "where id = :id\n";
    public static final String INSERT_INTO_NEWS =
            "with ins_author as (\n" +
                    "INSERT into author\n" +
                    "(id, name, surname)\n" +
                    "values (:author_id, :name, :surname)\n" +
                    "ON CONFLICT ON CONSTRAINT author_pkey\n" +
                    "DO UPDATE SET id = author.id RETURNING id)\n" +
                    "INSERT\n" +
                    "into news\n" +
                    "(id, author_id, title, shorttext, fulltext, creationdate, modificationdate)\n" +
                    "values " +
                    "(:id, (select id from ins_author), :title, :shorttext, :fulltext, :creationdate, :modificationdate)\n" +
                    "on conflict do nothing";
    public static final String UPDATE_NEWS =
            "with up_author as(\n" +
                    "UPDATE author\n" +
                    "SET\n" +
                    "name = :name,\n" +
                    "surname = :surname\n" +
                    "where id = :id\n" +
                    "returning id)\n" +
                    "UPDATE news\n" +
                    "SET\n" +
                    "title=:title, \n" +
                    "shorttext=:shorttext, \n" +
                    "fulltext= :fulltext, \n" +
                    "creationdate =:creationdate, \n" +
                    "modificationdate=:modificationdate\n" +
                    "where id =(select id from up_author)";
    public static final String COUNT = "SELECT count(*) from news";

    private NewsConstantQuery() {
    }
}
