package course.parceldelivery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class JdbcParcelRepository implements ParcelRepository {
  private final Database database;

  public JdbcParcelRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Parcel> findById(long id) throws SQLException {
    // TODO(B3): PreparedStatement ausführen und genau eine Zeile mappen.
    throw new UnsupportedOperationException("TODO B3 findById");
  }

  @Override
  public List<Parcel> findAll() throws SQLException {
    // TODO(B3): Alle parcels sortiert lesen.
    throw new UnsupportedOperationException("TODO B3 findAll");
  }

  private Parcel map(ResultSet row) throws SQLException {
    // TODO(B3): tracking_code, recipient und weight den Feldern
    // trackingCode, recipient, weight zuordnen.
    throw new UnsupportedOperationException("TODO B3 mapping");
  }
}
