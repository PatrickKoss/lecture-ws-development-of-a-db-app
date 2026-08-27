package course.hotel.repository;

import course.hotel.domain.RoomType;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRoomTypeRepository implements RoomTypeRepository {
  private final JdbcTemplate jdbc;

  public JdbcRoomTypeRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<RoomType> findAll() {
    throw new UnsupportedOperationException("TODO C2 findAll");
  }

  public Optional<RoomType> findById(long id) {
    throw new UnsupportedOperationException("TODO C2 findById");
  }

  public RoomType insert(RoomType value) {
    throw new UnsupportedOperationException("TODO C2 insert");
  }

  public boolean existsByTypeCode(String typeCode) {
    throw new UnsupportedOperationException("TODO C3 conflict");
  }
}
