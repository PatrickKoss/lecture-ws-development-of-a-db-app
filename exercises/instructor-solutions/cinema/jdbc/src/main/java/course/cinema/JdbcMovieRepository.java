package course.cinema;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class JdbcMovieRepository implements MovieRepository {
  private static final String SELECT_COLUMNS =
      "id, movie_code, title, release_year, duration_minutes, fsk_code, minimum_age";
  private final Database database;

  public JdbcMovieRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Movie> findById(long id) throws SQLException {
    try (var connection = database.open()) {
      return findById(connection, id);
    }
  }

  @Override
  public List<Movie> findAll() throws SQLException {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM movies ORDER BY id";
    var values = new ArrayList<Movie>();
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql);
        var rows = statement.executeQuery()) {
      while (rows.next()) values.add(map(rows));
    }
    return List.copyOf(values);
  }

  @Override
  public Movie insert(Movie value) throws SQLException {
    String sql =
        "INSERT INTO movies (movie_code, title, release_year, duration_minutes, fsk_code,"
            + " minimum_age) VALUES (?, ?, ?, ?, ?, ?)";
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, value.movieCode());
      statement.setString(2, value.title());
      statement.setInt(3, value.releaseYear());
      statement.setInt(4, value.durationMinutes());
      statement.setString(5, value.fskCode());
      statement.setInt(6, value.minimumAge());
      if (statement.executeUpdate() != 1) {
        throw new SQLException("Expected one inserted movies row");
      }

      long generatedId;
      try (var keys = statement.getGeneratedKeys()) {
        if (!keys.next()) throw new SQLException("Database returned no generated ID");
        generatedId = keys.getLong(1);
      }
      return findById(connection, generatedId)
          .orElseThrow(() -> new SQLException("Inserted movies row could not be read"));
    }
  }

  private Optional<Movie> findById(Connection connection, long id) throws SQLException {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM movies WHERE id = ?";
    try (var statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      try (var rows = statement.executeQuery()) {
        return rows.next() ? Optional.of(map(rows)) : Optional.empty();
      }
    }
  }

  private Movie map(ResultSet row) throws SQLException {
    return new Movie(
        row.getLong("id"),
        row.getString("movie_code"),
        row.getString("title"),
        row.getInt("release_year"),
        row.getInt("duration_minutes"),
        row.getString("fsk_code"),
        row.getInt("minimum_age"));
  }
}
