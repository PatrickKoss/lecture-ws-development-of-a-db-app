package course.parceldelivery.repository;

import course.parceldelivery.domain.Parcel;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcParcelRepository implements ParcelRepository {
  private final JdbcTemplate jdbc;

  public JdbcParcelRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<Parcel> findAll() {
    throw new UnsupportedOperationException("TODO C2 findAll");
  }

  public Optional<Parcel> findById(long id) {
    throw new UnsupportedOperationException("TODO C2 findById");
  }

  public Parcel insert(Parcel value) {
    throw new UnsupportedOperationException("TODO C2 insert");
  }

  public boolean existsByTrackingCode(String trackingCode) {
    throw new UnsupportedOperationException("TODO C3 conflict");
  }
}
