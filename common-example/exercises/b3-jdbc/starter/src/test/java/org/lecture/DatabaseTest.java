package org.lecture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Files;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class DatabaseTest {
    @Test
    void enablesForeignKeysAndRunsPreparedLookup() throws Exception {
        var file = Files.createTempFile("university-b3-", ".db");
        try {
            var database = new Database("jdbc:sqlite:" + file);
            database.initialize();
            try (var connection = database.open();
                    var rows = connection.createStatement().executeQuery("PRAGMA foreign_keys")) {
                rows.next();
                assertEquals(1, rows.getInt(1));
            }
            assertEquals(java.util.List.of("INF", "MAT", "WI"), new DepartmentLookup(database).findCodes());
            try (var connection = database.open();
                    var statement = connection.prepareStatement(
                            "INSERT INTO courses(course_code,title,credits,lecturer_id) VALUES (?,?,?,?)")) {
                statement.setString(1, "BAD-1");
                statement.setString(2, "Ungültiger Kurs");
                statement.setInt(3, 5);
                statement.setLong(4, 99999);
                assertThrows(SQLException.class, statement::executeUpdate);
            }
        } finally {
            Files.deleteIfExists(file);
        }
    }
}
