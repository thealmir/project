package ru.mingazov.repositories;

import ru.mingazov.models.Admin;

import javax.sql.DataSource;

public class AdminsRepositoryJdbc implements AdminsRepository {

    private JdbcTemplate template;

    //language=SQL
    private String SQL_GET_PASSWORD = "select password from admins where login = ?";

    private RowMapper<Admin> mapper =
            row -> Admin.builder().password(row.getString("password")).build();

    public AdminsRepositoryJdbc(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public String getPassword(String login) {
        Admin admin = template.query(SQL_GET_PASSWORD, mapper, login).get(0);
        return admin.getPassword();
    }
}
