package co.bytesarc.workoutsapi.exercises.repositories;

import co.bytesarc.workoutsapi.exercises.models.Exercise;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class ExerciseRepository {

    private final JdbcTemplate jdbcTemplate;

    public ExerciseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static String SCHEMA_NAME = "exercises_schema";
    private static String EXERCISE_TABLE_NAME = "exercise";
    private static String USER_EXERCISE_TABLE_NAME = "user_exercise";

    private final RowMapper<Exercise> exerciseRowMapper = (rs, rowNum) -> {
        String trimmedDescription = rs.getString("description");
        if (trimmedDescription != null) {
            trimmedDescription = trimmedDescription.trim();
        }
        return new Exercise(rs.getInt("id"), rs.getString("name").trim(),
                rs.getString("type").trim(), trimmedDescription,
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime());
    };

    public List<Exercise> getExercises(int userId) {
        return Stream.concat(getUserExercises(userId).stream(), getDefaultExercises().stream())
                .collect(Collectors.toList());
    }

    private List<Exercise> getUserExercises(int userId) {
        String sql = "SELECT * FROM " + SCHEMA_NAME + "." + USER_EXERCISE_TABLE_NAME;

        return jdbcTemplate.query(sql, exerciseRowMapper);
    }

    private List<Exercise> getDefaultExercises() {
        String sql = "SELECT * FROM " + SCHEMA_NAME + "." + EXERCISE_TABLE_NAME;

        return jdbcTemplate.query(sql, exerciseRowMapper);
    }

    public Exercise createExercise(int userId, String exerciseName, String exerciseType,
            String description) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("INSERT INTO ").append(SCHEMA_NAME).append('.')
                .append(USER_EXERCISE_TABLE_NAME)
                .append(" (name,type,description,defined_by_user_id)").append(" VALUES (?,?,?,?)")
                .append("RETURNING id, name , type, description, defined_by_user_id,created_at, updated_at");

        return jdbcTemplate.query(sqlBuilder.toString(), exerciseRowMapper, exerciseName,
                exerciseType, description, userId).stream().findFirst().orElse(null);
    }
}
