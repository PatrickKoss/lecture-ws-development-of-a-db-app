package course.library;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class JdbcBookRepository implements BookRepository {
  private static final String SELECT_COLUMNS =
      "id, isbn, title, publication_year, subject_area, shelf_code";
  private final Database database;

  public JdbcBookRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Book> findById(long id) {
    try (var connection = database.open()) {
      return findById(connection, id);
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  @Override
  public List<Book> findAll() {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM books ORDER BY id";
    var values = new ArrayList<Book>();
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql);
        var rows = statement.executeQuery()) {
      while (rows.next()) values.add(map(rows));
      return List.copyOf(values);
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  @Override
  public Book insert(Book value) {
    String sql =
        "INSERT INTO books (isbn, title, publication_year, subject_area, shelf_code) VALUES (?, ?,"
            + " ?, ?, ?)";
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, value.isbn());
      statement.setString(2, value.title());
      statement.setInt(3, value.publicationYear());
      statement.setString(4, value.subjectArea());
      statement.setString(5, value.shelfCode());
      if (statement.executeUpdate() != 1) {
        throw new SQLException("Expected one inserted books row");
      }

      long generatedId;
      try (var keys = statement.getGeneratedKeys()) {
        if (!keys.next()) throw new SQLException("Database returned no generated ID");
        generatedId = keys.getLong(1);
      }
      return findById(connection, generatedId)
          .orElseThrow(() -> new SQLException("Inserted books row could not be read"));
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  private Optional<Book> findById(Connection connection, long id) throws SQLException {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM books WHERE id = ?";
    try (var statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      try (var rows = statement.executeQuery()) {
        return rows.next() ? Optional.of(map(rows)) : Optional.empty();
      }
    }
  }

  private Book map(ResultSet row) throws SQLException {
    return new Book(
        row.getLong("id"),
        row.getString("isbn"),
        row.getString("title"),
        row.getInt("publication_year"),
        row.getString("subject_area"),
        row.getString("shelf_code"));
  }

  private RepositoryException translate(SQLException exception) {
    String message = exception.getMessage();
    if (message != null && message.contains("UNIQUE constraint failed")) {
      return new DuplicateKeyException("The isbn is already in use", exception);
    }
    return new RepositoryException("Could not access books", exception);
  }
}
