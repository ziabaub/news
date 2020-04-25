package com.epam.lab.statics.data.query;

public class AuthorConstantQuery {
    public static final String DELETE_ALL = "TRUNCATE author RESTART IDENTITY CASCADE";
    public static final String DELETE_AUTHOR_BY_ID =
            "DELETE FROM author\n" +
                    "WHERE id = :id";
    public static final String SELECT_ALL_AUTHOR =
            "SELECT *\n" +
                    "FROM author\n";
    public static final String SELECT_AUTHOR_BY_ID =
            SELECT_ALL_AUTHOR +
                    " WHERE id = :id";
    public static final String INSERT_INTO_AUTHOR =
            "INSERT INTO author\n" +
                    "(id,name,surname)\n" +
                    "VALUES\n" +
                    "(:id,:name,:surname)\n" +
                    "ON CONFLICT DO NOTHING";
    public static final String UPDATE_AUTHOR =
            "UPDATE author\n" +
                    "SET\n" +
                    "name = :name," +
                    "surname = :surname\n" +
                    "WHERE id = :id";

    private AuthorConstantQuery() {
    }
}
