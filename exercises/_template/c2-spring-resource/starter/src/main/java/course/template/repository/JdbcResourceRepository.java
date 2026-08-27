package course.template.repository;

import course.template.domain.Resource;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcResourceRepository implements ResourceRepository {
  private final JdbcTemplate jdbc;

  public JdbcResourceRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<Resource> findAll() {
    throw new UnsupportedOperationException("TODO C2 findAll");
  }

  public Optional<Resource> findById(long id) {
    throw new UnsupportedOperationException("TODO C2 findById");
  }

  public Resource insert(Resource value) {
    throw new UnsupportedOperationException("TODO C2 insert");
  }

  public boolean existsByResourceCode(String resourceCode) {
    throw new UnsupportedOperationException("TODO C3 conflict");
  }
}
