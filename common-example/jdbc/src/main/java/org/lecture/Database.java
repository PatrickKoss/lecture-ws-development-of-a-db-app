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

    public static Database courseDatabase(String url) {
        return new Database(url, Path.of("../sql/schema.sql"), Path.of("../sql/seed.sql"));
    }

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
            executeScript(connection, schema);
            executeScript(connection, seed);
        }
    }

    private static void executeScript(Connection connection, Path path)
            throws IOException, SQLException {
        String sql = Files.readString(path).replaceAll("(?m)^\\s*--.*$", "");
        for (String part : sql.split(";")) {
            if (!part.isBlank()) {
                try (var statement = connection.createStatement()) {
                    statement.execute(part);
                }
            }
        }
    }
}
