package co.bytesarc.workoutsapi.users.repository;

import co.bytesarc.workoutsapi.users.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class AuthRepository {

    private final JdbcTemplate jdbcTemplate;

    public AuthRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            rs.getInt("id"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getTimestamp("created_at").toLocalDateTime(),
            rs.getTimestamp("updated_at").toLocalDateTime()
    );

    public User findUserByEmail(String email){
      String sql = """
              SELECT * FROM workouts_users_schema.workouts_user WHERE email = ?
              """;

      return jdbcTemplate.query(sql, userRowMapper, email) .stream().findFirst().orElse(null);
    };

    public User createUser(String email, String hashedPassword){
        String sql = """
                INSERT INTO workouts_users_schema.workouts_user (email, password)
                VALUES (?,?)
                RETURNING id, email, password, created_at, updated_at
                """;
        return jdbcTemplate.query(sql,userRowMapper, email, hashedPassword).stream().findFirst().orElse(null);
    };
}
