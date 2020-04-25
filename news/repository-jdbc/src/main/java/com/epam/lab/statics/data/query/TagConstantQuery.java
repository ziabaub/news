package com.epam.lab.statics.data.query;

public class TagConstantQuery {
    public static final String DELETE_ALL = "TRUNCATE tag RESTART IDENTITY CASCADE";
    public static final String DELETE_TAG_BY_ID =
            "DELETE FROM tag\n" +
                    "WHERE id = :id";
    public static final String SELECT_ALL_TAG =
            "SELECT * FROM tag\n";
    public static final String SELECT_TAG_BY_ID =
            SELECT_ALL_TAG +
                    "WHERE id = :id";

    public static final String INSERT_INTO_TAG =
            "INSERT INTO tag\n" +
                    "(id,name)\n" +
                    "VALUES\n" +
                    "(:id ,:name)\n" +
                    "ON CONFLICT DO NOTHING";
    public static final String UPDATE_TAG =
            "UPDATE tag\n" +
                    "SET\n" +
                    "name=:name\n" +
                    "WHERE id = :id";

    private TagConstantQuery() {
    }
}
