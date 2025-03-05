package co.bytesarc.workoutsapi.exercises.repositories;

import co.bytesarc.workoutsapi.exercises.models.Exercise;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExerciseRepository {

    private final JdbcTemplate jdbcTemplate;

    public ExerciseRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Exercise> exerciseRowMapper = (rs, rowNum) -> {
        String trimmedDescription =  rs.getString("description");
        if (trimmedDescription !=null){
            trimmedDescription = trimmedDescription.trim();
        }
        return new Exercise(
                rs.getInt("id"),
                rs.getString("name").trim(),
                rs.getString("type").trim(),
                trimmedDescription,
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    };

    public List<Exercise> getExercises(){
        String sql = """
                SELECT * FROM exercises_schema.exercise
                """;

        return jdbcTemplate.query(sql, exerciseRowMapper);
    }

}