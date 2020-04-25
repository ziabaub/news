package com.epam.lab.statics.data.query;

public class UserConstantQuery {
    public static final String DELETE_ALL = "TRUNCATE users RESTART IDENTITY CASCADE";
    public static final String DELETE_USER_BY_ID =
            "DELETE FROM users\n" +
                    "WHERE roles_id = :roles_id";
    public static final String SELECT_ALL_USER =
            "SELECT\n" +
                    "u.roles_id, name, surname, login, password, r.role\n" +
                    "FROM users as u\n" +
                    "inner join roles as r on r.id = u.roles_id\n";
    public static final String SELECT_USER_BY_ID =
            SELECT_ALL_USER +
                    "WHERE u.roles_id = :roles_id";
    public static final String SELECT_USER_BY_LOGIN =
            SELECT_ALL_USER +
                    "WHERE login = :login";
    public static final String INSERT_INTO_USER =
            "with ins_users as (\n" +
                    "INSERT INTO public.users " +
                    "(roles_id, name, surname, login, password)\n" +
                    "VALUES\n" +
                    "(:roles_id, :name, :surname, :login, :password)\n" +
                    "ON CONFLICT ON CONSTRAINT users_pkey\n" +
                    "DO UPDATE SET roles_id = users.roles_id RETURNING roles_id)\n" +
                    "insert into roles" +
                    "(id, role)\n" +
                    "values " +
                    "((select roles_id from ins_users), :role)\n" +
                    "ON CONFLICT DO NOTHING;";
    public static final String UPDATE_USER =
            "with up_users as (\n" +
                    "UPDATE public.users\n" +
                    "SET\n" +
                    "name = :name,\n" +
                    "surname = :surname,\n" +
                    "login = :login,\n" +
                    "password = :password\n" +
                    "where roles_id = :roles_id\n" +
                    "RETURNING roles_id)\n" +
                    "update roles\n" +
                    "SET role = :role\n" +
                    "where id = (select id from up_users);";

    private UserConstantQuery() {
    }
}
