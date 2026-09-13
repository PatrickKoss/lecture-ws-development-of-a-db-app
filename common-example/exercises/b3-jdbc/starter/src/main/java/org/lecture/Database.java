package org.lecture;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private final String url;

    public Database(String url) { this.url = url; }

    public Connection open() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        try (var statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON");
        } catch (SQLException exception) {
            connection.close();
            throw exception;
        }
        return connection;
    }

    public void initialize() throws SQLException, IOException {
        try (Connection connection = open()) {
            run(connection, "/db/V1__schema.sql");
            run(connection, "/db/V2__seed.sql");
        }
    }

    private void run(Connection connection, String path) throws SQLException, IOException {
        String sql;
        try (var input = Database.class.getResourceAsStream(path)) {
            if (input == null) throw new IOException("Missing resource " + path);
            sql = new String(input.readAllBytes(), StandardCharsets.UTF_8)
                    .replaceAll("(?m)^\\s*--.*$", "");
        }
        for (String part : sql.split(";")) {
            if (!part.isBlank()) {
                try (var statement = connection.createStatement()) { statement.execute(part); }
            }
        }
    }
}
