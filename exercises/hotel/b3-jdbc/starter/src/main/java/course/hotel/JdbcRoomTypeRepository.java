package course.hotel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class JdbcRoomTypeRepository implements RoomTypeRepository {
  private final Database database;

  public JdbcRoomTypeRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<RoomType> findById(long id) throws SQLException {
    // TODO(B3): PreparedStatement ausführen und genau eine Zeile mappen.
    throw new UnsupportedOperationException("TODO B3 findById");
  }

  @Override
  public List<RoomType> findAll() throws SQLException {
    // TODO(B3): Alle room_types sortiert lesen.
    throw new UnsupportedOperationException("TODO B3 findAll");
  }

  private RoomType map(ResultSet row) throws SQLException {
    // TODO(B3): type_code, name und standard_price_cents den Feldern
    // typeCode, name, capacity, standardPriceCents zuordnen.
    throw new UnsupportedOperationException("TODO B3 mapping");
  }
}
