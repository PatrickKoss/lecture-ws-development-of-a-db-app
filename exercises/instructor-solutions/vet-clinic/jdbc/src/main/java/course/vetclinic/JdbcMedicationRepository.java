package course.vetclinic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class JdbcMedicationRepository implements MedicationRepository {
  private static final String SELECT_COLUMNS =
      "id, pzn, product_name, active_ingredient, dosage_form, prescription_required, active";
  private final Database database;

  public JdbcMedicationRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Medication> findById(long id) throws SQLException {
    try (var connection = database.open()) {
      return findById(connection, id);
    }
  }

  @Override
  public List<Medication> findAll() throws SQLException {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM medications ORDER BY id";
    var values = new ArrayList<Medication>();
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql);
        var rows = statement.executeQuery()) {
      while (rows.next()) values.add(map(rows));
    }
    return List.copyOf(values);
  }

  @Override
  public Medication insert(Medication value) throws SQLException {
    String sql =
        "INSERT INTO medications (pzn, product_name, active_ingredient, dosage_form,"
            + " prescription_required, active) VALUES (?, ?, ?, ?, ?, ?)";
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, value.pzn());
      statement.setString(2, value.productName());
      statement.setString(3, value.activeIngredient());
      statement.setString(4, value.dosageForm());
      statement.setBoolean(5, value.prescriptionRequired());
      statement.setBoolean(6, value.active());
      if (statement.executeUpdate() != 1) {
        throw new SQLException("Expected one inserted medications row");
      }

      long generatedId;
      try (var keys = statement.getGeneratedKeys()) {
        if (!keys.next()) throw new SQLException("Database returned no generated ID");
        generatedId = keys.getLong(1);
      }
      return findById(connection, generatedId)
          .orElseThrow(() -> new SQLException("Inserted medications row could not be read"));
    }
  }

  private Optional<Medication> findById(Connection connection, long id) throws SQLException {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM medications WHERE id = ?";
    try (var statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      try (var rows = statement.executeQuery()) {
        return rows.next() ? Optional.of(map(rows)) : Optional.empty();
      }
    }
  }

  private Medication map(ResultSet row) throws SQLException {
    return new Medication(
        row.getLong("id"),
        row.getString("pzn"),
        row.getString("product_name"),
        row.getString("active_ingredient"),
        row.getString("dosage_form"),
        row.getBoolean("prescription_required"),
        row.getBoolean("active"));
  }
}
