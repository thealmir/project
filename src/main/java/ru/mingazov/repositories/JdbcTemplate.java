package ru.mingazov.repositories;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JdbcTemplate {

    private final DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object ... args) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            resultSet = statement.executeQuery();
            List<T> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(rowMapper.mapRow(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    //ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    // ignore
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    // ignore
                }
            }
        }
    }

    public void update(String sql, Object ... args) {
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            // single row update insert into ... values (...);
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void updateBatch(String sql, Object ... args) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // case files inserting for single thread
            // multiple rows update insert into ... values (...), (...) ...;
            List<Object> arguments = new ArrayList<>();

            for (Object arg : args) {

                boolean list = false;

                if (arg.getClass().getInterfaces()[0] == List.class) {
                    arguments.addAll((ArrayList<Object>) arg);
                    list = true;
                }

                if (!list) {
                    arguments.add(arg);
                }

            }

            for (int i = 1; i < arguments.size(); i++) {
                statement.setObject(1, arguments.get(0));
                statement.setObject(2, arguments.get(i));
                statement.addBatch();
            }
            statement.executeBatch();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


}
