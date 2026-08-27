package course.carworkshop.repository;

import course.carworkshop.domain.Part;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcPartRepository implements PartRepository {
  private final JdbcTemplate jdbc;

  public JdbcPartRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<Part> findAll() {
    throw new UnsupportedOperationException("TODO C2 findAll");
  }

  public Optional<Part> findById(long id) {
    throw new UnsupportedOperationException("TODO C2 findById");
  }

  public Part insert(Part value) {
    throw new UnsupportedOperationException("TODO C2 insert");
  }

  public boolean existsByPartNumber(String partNumber) {
    throw new UnsupportedOperationException("TODO C3 conflict");
  }
}
