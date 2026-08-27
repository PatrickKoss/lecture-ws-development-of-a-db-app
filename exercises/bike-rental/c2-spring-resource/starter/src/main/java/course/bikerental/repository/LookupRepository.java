package course.bikerental.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LookupRepository {
  private final JdbcTemplate jdbc;

  public LookupRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<String> findAllLabels() {
    return jdbc.queryForList("SELECT model_name FROM bike_models ORDER BY id", String.class);
  }
}
