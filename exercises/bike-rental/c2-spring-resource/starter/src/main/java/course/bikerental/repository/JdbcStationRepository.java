package course.bikerental.repository;

import course.bikerental.domain.Station;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcStationRepository implements StationRepository {
  private final JdbcTemplate jdbc;

  public JdbcStationRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<Station> findAll() {
    throw new UnsupportedOperationException("TODO C2 findAll");
  }

  public Optional<Station> findById(long id) {
    throw new UnsupportedOperationException("TODO C2 findById");
  }

  public Station insert(Station value) {
    throw new UnsupportedOperationException("TODO C2 insert");
  }

  public boolean existsByStationCode(String stationCode) {
    throw new UnsupportedOperationException("TODO C3 conflict");
  }
}
