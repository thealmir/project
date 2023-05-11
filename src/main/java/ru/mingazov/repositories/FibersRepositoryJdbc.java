package ru.mingazov.repositories;

import ru.mingazov.models.Fiber;
import ru.mingazov.models.File;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FibersRepositoryJdbc implements FibersRepository {

    // for ajax requests
    //language=SQL
    private static String SQL_GET_LAST_FIBERS =
            "select fibs.id, fibs.section, fibs.creation_date at time zone 'utc' at time zone 'Europe/Moscow' as creation," +
            "array_agg(fs.id) as files_id, array_agg(fs.name) as files_names, fibs.comment_to " +
            "from fibers fibs " +
            "left join files fs on fs.fiber_id = fibs.id " +
            "where fibs.id > ? and fibs.comment_to = ?" +
            "group by fibs.id " +
            "order by creation desc";

    //language=SQL
    private static String SQL_GET_LAST_OPENING_FIBERS =
            "select fibs.id, fibs.section, fibs.creation_date at time zone 'utc' at time zone 'Europe/Moscow' as creation," +
                    "array_agg(fs.id) as files_id, array_agg(fs.name) as files_names, fibs.comment_to " +
                    "from fibers fibs " +
                    "left join files fs on fs.fiber_id = fibs.id " +
                    "where fibs.id > ? and fibs.comment_to is null " +
                    "group by fibs.id " +
                    "order by creation desc";

    //language=SQL
    private static String SQL_DELETE_FIBER = "delete from fibers where id = ?";

    //language=SQL
    private static String SQL_FIND_ALL =
            "select fibs.id, fibs.section, fibs.creation_date at time zone 'utc' at time zone 'Europe/Moscow' as creation," +
            "array_agg(fs.id) as files_id, array_agg(fs.name) as files_names, fibs.comment_to " +
            "from fibers fibs " +
            "left join files fs on fs.fiber_id = fibs.id group by fibs.id order by creation desc";

    //language=SQL
    private static String SQL_FIND_ALL_OF =
            "select fibs.id, fibs.section, fibs.creation_date at time zone 'utc' at time zone 'Europe/Moscow' as creation," +
            "array_agg(fs.id) as files_id, array_agg(fs.name) as files_names " +
            "from fibers fibs " +
            "left join files fs on fs.fiber_id = fibs.id where comment_to is null group by fibs.id order by creation desc";
    //language=SQL
    private static String SQL_FIND_FIBER_BY_ID =
            "select fibs.id, fibs.section, fibs.creation_date at time zone 'utc' at time zone 'Europe/Moscow' as creation," +
            "fibs.comment_to, array_agg(fs.id) as files_id, array_agg(fs.name) as files_names " +
            "from fibers fibs " +
            "left join files fs on fs.fiber_id = fibs.id where fibs.id = ? group by fibs.id order by creation desc";
    //language=SQL
    private static String SQL_INSERT_FIBER = "insert into fibers(section, creation_date, comment_to) values" +
            "(?, current_timestamp, ?) returning id";
    //language=SQL
    private static String SQL_FIND_ALL_COMMENTS =
            "with recursive comments" +
            "   as(" +
                    "select id, section, creation_date at time zone 'utc' at time zone 'Europe/Moscow' as creation,"+
                    "comment_to from fibers where comment_to = ? "+
                    "union "+
                    "select f.id, f.section, f.creation_date at time zone 'utc' at time zone 'Europe/Moscow' as creation, " +
                            "f.comment_to "+
                    "from fibers f " +
                    "inner join comments cms on f.comment_to = cms.id " +
                ") " +
            "select cms.id, cms.section, cms.creation, cms.comment_to, " +
            "array_agg(fs.id) as files_id, array_agg(fs.name) as files_names "+
            "from comments cms " +
            "left join files fs on fs.fiber_id = cms.id " +
            "group by cms.id, cms.section, cms.creation, cms.comment_to " +
            "order by creation";

    private JdbcTemplate jdbcTemplate;

    private List<File> fetchFiles(ResultSet row) throws SQLException {
        Long[] fileIds = (Long[]) row.getArray("files_id").getArray();
        String[] fileNames = (String[]) row.getArray("files_names").getArray();
        List<File> files = new ArrayList<>();

        if (fileIds[0] == null) return null;

        for (int i = 0; i < fileIds.length; i++) {
            files.add(
                    File.builder()
                        .id(fileIds[i])
                        .name(fileNames[i])
                        .build()
            );
        }
        return files;
    }

    RowMapper<Fiber> mapper = row ->
            Fiber.builder()
                    .id(row.getLong("id"))
                    .section(row.getString("section"))
                    .creationDate(row.getTimestamp("creation").toLocalDateTime())
                    .commentTo(row.getLong("comment_to") == 0 ? null : row.getLong("comment_to"))
                    .files(row.getArray("files_id") == null ? null : fetchFiles(row))
                    .build();

    RowMapper<Fiber> openingFiberMapper = row ->
            Fiber.builder()
                    .id(row.getLong("id"))
                    .section(row.getString("section"))
                    .creationDate(row.getTimestamp("creation").toLocalDateTime())
                    .commentTo(null)
                    .files(fetchFiles(row))
                    .build();

    RowMapper<Fiber> fiberMapperId = row ->
            Fiber.builder()
                    .id(row.getLong("id"))
                    .build();

    public FibersRepositoryJdbc(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long save(Fiber entity) {
        return jdbcTemplate.query(SQL_INSERT_FIBER, fiberMapperId, entity.getSection(), entity.getCommentTo())
                .get(0)
                .getId();
    }

    @Override
    public void update(Fiber entity) {

    }

    @Override
    public void delete(Fiber entity) {
        jdbcTemplate.update(SQL_DELETE_FIBER, entity.getId());
    }

    @Override
    public List<Fiber> findAll() {
        List<Fiber> temp = jdbcTemplate.query(SQL_FIND_ALL, mapper);
        System.out.println(temp);
        return temp;
    }

    @Override
    public List<Fiber> findLastFibers(Long id) {
        return jdbcTemplate.query(SQL_GET_LAST_OPENING_FIBERS, mapper, id);
    }

    @Override
    public List<Fiber> findLastCommentFibers(Long lastId, Long threadId) {
        return jdbcTemplate.query(SQL_GET_LAST_FIBERS, mapper, lastId, threadId);
    }

    @Override
    public Fiber findById(Long id) {
        List<Fiber> fiber = jdbcTemplate.query(SQL_FIND_FIBER_BY_ID, mapper, id);
        if (fiber.size() != 1) {
            throw new IllegalArgumentException("Fiber not found");
        }
        return fiber.get(0);
    }

    @Override
    public List<Fiber> findAllComments(Long id) {
        return jdbcTemplate.query(SQL_FIND_ALL_COMMENTS, mapper, id);
    }

    @Override
    public List<Fiber> findAllOpeningFibers() {
        return jdbcTemplate.query(SQL_FIND_ALL_OF, openingFiberMapper);
    }
}
