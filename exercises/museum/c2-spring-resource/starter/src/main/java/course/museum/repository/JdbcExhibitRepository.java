package course.museum.repository;

import course.museum.domain.Exhibit;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcExhibitRepository implements ExhibitRepository {
  private final JdbcTemplate jdbc;

  public JdbcExhibitRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<Exhibit> findAll() {
    throw new UnsupportedOperationException("TODO C2 findAll");
  }

  public Optional<Exhibit> findById(long id) {
    throw new UnsupportedOperationException("TODO C2 findById");
  }

  public Exhibit insert(Exhibit value) {
    throw new UnsupportedOperationException("TODO C2 insert");
  }

  public boolean existsByInventoryCode(String inventoryCode) {
    throw new UnsupportedOperationException("TODO C3 conflict");
  }
}
