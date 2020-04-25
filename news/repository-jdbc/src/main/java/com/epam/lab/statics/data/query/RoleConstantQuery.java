package com.epam.lab.statics.data.query;

public class RoleConstantQuery {
    public static final String DELETE_ALL = "TRUNCATE roles RESTART IDENTITY CASCADE";
    public static final String DELETE_ROLE_BY_ID =
            "DELETE FROM roles\n" +
                    "WHERE id = :id";
    public static final String SELECT_ALL_ROLE = "SELECT * FROM roles\n";
    public static final String SELECT_ROLE_BY_ID =
            SELECT_ALL_ROLE +
                    "WHERE id = :id";
    public static final String INSERT_INTO_ROLE =
            "INSERT INTO roles\n" +
                    "(id, role)\n" +
                    "VALUES\n" +
                    "(:id,:role)\n" +
                    "ON CONFLICT DO NOTHING";
    public static final String UPDATE_ROLE =
            "UPDATE roles\n" +
                    "SET\n" +
                    "role=:role\n" +
                    "WHERE id = :id";

    private RoleConstantQuery() {
    }
}
