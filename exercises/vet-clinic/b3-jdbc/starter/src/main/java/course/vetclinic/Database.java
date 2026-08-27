package course.vetclinic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Collectors;

public final class Database {
  private final String url;

  public Database(String url) {
    this.url = url;
  }

  public Connection open() throws SQLException {
    Connection connection = DriverManager.getConnection(url);
    connection.createStatement().execute("PRAGMA foreign_keys = ON");
    return connection;
  }

  public void initialize() throws SQLException, IOException {
    try (Connection connection = open()) {
      run(connection, "/db/V1__schema.sql");
      run(connection, "/db/V2__seed.sql");
    }
  }

  private void run(Connection connection, String path) throws SQLException, IOException {
    try (var input = Database.class.getResourceAsStream(path)) {
      if (input == null) throw new IOException("Missing resource " + path);
      String sql = new String(input.readAllBytes(), StandardCharsets.UTF_8);
      sql =
          sql.lines()
              .filter(line -> !line.stripLeading().startsWith("--"))
              .collect(Collectors.joining("\n"));
      for (String statement : sql.split(";")) {
        if (!statement.isBlank()) connection.createStatement().execute(statement);
      }
    }
  }
}
