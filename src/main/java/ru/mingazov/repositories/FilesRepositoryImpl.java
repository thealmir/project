package ru.mingazov.repositories;

import ru.mingazov.models.File;

import javax.sql.DataSource;
import java.util.List;

public class FilesRepositoryImpl implements FilesRepository {

    private JdbcTemplate template;

    //language=SQL
    private static String SQL_INSERT_FILES = "insert into files(fiber_id, name) values" +
            "(?, ?)";
    //language=SQL
    private static String SQL_GET_NAME_BY_ID = "select name from files where id = ?";

    public FilesRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    private RowMapper<File> fileNameMapper = row -> File.builder().name(row.getString("name")).build();

    @Override
    public void save(Long fiberID, List<String> fileNames) {
        template.updateBatch(SQL_INSERT_FILES, fiberID, fileNames);
    }

    @Override
    public File getNameById(Long id) {
        return template.query(SQL_GET_NAME_BY_ID, fileNameMapper, id).get(0);
    }


}
