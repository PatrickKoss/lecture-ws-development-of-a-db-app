package org.lecture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private final String url;
    private final Path schema;
    private final Path seed;

    public Database(String url, Path schema, Path seed) {
        this.url = url;
        this.schema = schema;
        this.seed = seed;
    }

    Connection open() throws SQLException {
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
            run(connection, schema);
            run(connection, seed);
        }
    }

    private void run(Connection connection, Path path) throws SQLException, IOException {
        String sql = Files.readString(path).replaceAll("(?m)^\\s*--.*$", "");
        for (String part : sql.split(";")) {
            if (!part.isBlank()) {
                try (var statement = connection.createStatement()) { statement.execute(part); }
            }
        }
    }
}
