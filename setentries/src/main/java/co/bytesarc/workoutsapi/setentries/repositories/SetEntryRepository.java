package co.bytesarc.workoutsapi.setentries.repositories;

import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import co.bytesarc.workoutsapi.setentries.models.SetEntry;

@Repository
public class SetEntryRepository {
    private final JdbcTemplate jdbcTemplate;

    private static String SET_ENTRY_TABLE = "set_entry";
    private static String SET_ENTRIES_SCHEMA = "set_entries_schema";

    public SetEntryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<SetEntry> setEntryMapper = (rs, rowNr) -> {
        return new SetEntry(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("exercise_id"),
                rs.getDouble("weight"), rs.getInt("reps"),
                rs.getTimestamp("created_at").toInstant(),
                rs.getTimestamp("updated_at").toInstant());
    };

    public List<SetEntry> getSetEntries(int userId) {
        String sql = "SELECT * FROM " + SET_ENTRIES_SCHEMA + '.' + SET_ENTRY_TABLE
                + " WHERE user_id = ?";

        return jdbcTemplate.query(sql, setEntryMapper, userId);
    }

    public SetEntry createSetEntry(int userId, int exerciseId, double weights, int reps,
            String note) {
        String sql = "INSERT INTO " + SET_ENTRIES_SCHEMA + '.' + SET_ENTRY_TABLE
                + " (user_id, exercise_id, weight, reps, note) VALUES (?, ?, ?, ?, ?) RETURNING id, user_id, exercise_id, weight, reps, created_at, updated_at";

        return jdbcTemplate.query(sql, setEntryMapper, userId, exerciseId, weights, reps, note)
                .stream().findFirst().orElse(null);
    }

    public SetEntry updateSetEntry(int id, Double weight, Integer reps) {
        StringBuffer sqlBuilder = new StringBuffer();
        List<Object> args = new ArrayList<>();
        sqlBuilder.append("UPDATE ").append(SET_ENTRIES_SCHEMA).append('.').append(SET_ENTRY_TABLE)
                .append(" SET ");
        if (weight != null) {
            sqlBuilder.append("weight = ?, ");
            args.add(weight);
        }
        if (reps != null) {
            sqlBuilder.append("reps = ? ");
            args.add(reps);
        }

        sqlBuilder.append(
                "WHERE id = ? RETURNING id, user_id, exercise_id, weight, reps, created_at, updated_at");
        args.add(id);
        String sql = sqlBuilder.toString();

        return jdbcTemplate.query(sql, setEntryMapper, args.toArray()).stream().findFirst()
                .orElse(null);
    }

    public void deleteSetEntry(int id) {
        String sql = "DELETE FROM " + SET_ENTRIES_SCHEMA + '.' + SET_ENTRY_TABLE + " WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
