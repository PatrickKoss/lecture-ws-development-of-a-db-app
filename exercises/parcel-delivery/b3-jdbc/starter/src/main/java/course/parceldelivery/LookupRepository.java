package course.parceldelivery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class LookupRepository {
  private final Database database;

  public LookupRepository(Database database) {
    this.database = database;
  }

  public List<String> findAllLabels() throws SQLException {
    var values = new ArrayList<String>();
    try (var connection = database.open();
        var statement =
            connection.prepareStatement("SELECT city FROM depots ORDER BY id");
        var rows = statement.executeQuery()) {
      while (rows.next()) values.add(rows.getString(1));
    }
    return values;
  }
}
