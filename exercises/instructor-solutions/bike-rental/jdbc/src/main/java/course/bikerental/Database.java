package course.bikerental;

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
    try (var statement = connection.createStatement()) {
      statement.execute("PRAGMA foreign_keys = ON");
      return connection;
    } catch (SQLException exception) {
      try {
        connection.close();
      } catch (SQLException closeFailure) {
        exception.addSuppressed(closeFailure);
      }
      throw exception;
    }
  }

  public void initialize() throws SQLException, IOException {
    try (Connection connection = open()) {
      connection.setAutoCommit(false);
      try {
        run(connection, "/db/V1__schema.sql");
        run(connection, "/db/V2__seed.sql");
        connection.commit();
      } catch (SQLException | IOException | RuntimeException exception) {
        try {
          connection.rollback();
        } catch (SQLException rollbackFailure) {
          exception.addSuppressed(rollbackFailure);
        }
        throw exception;
      }
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
      for (String rawStatement : sql.split(";")) {
        String statementText = rawStatement.strip();
        if (statementText.isEmpty()
            || statementText.equalsIgnoreCase("BEGIN TRANSACTION")
            || statementText.equalsIgnoreCase("COMMIT")) {
          continue;
        }
        try (var statement = connection.createStatement()) {
          statement.execute(statementText);
        }
      }
    }
  }
}
