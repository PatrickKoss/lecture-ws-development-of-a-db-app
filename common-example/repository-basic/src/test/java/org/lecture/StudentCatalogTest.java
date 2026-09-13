package org.lecture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class StudentCatalogTest {
    @Test
    void readsCanonicalSeedThroughCatalog() throws Exception {
        Path file = Files.createTempFile("repository-basic-", ".db");
        try {
            var database = database(file);
            database.initialize();
            var catalog = new StudentCatalog(new JdbcStudentRepository(database));

            assertEquals(20, catalog.listStudents().size());
            assertEquals("M2023001", catalog.findStudent(1).orElseThrow().studentNumber());
            assertTrue(catalog.findStudent(99999).isEmpty());
            assertEquals(
                    List.of("INF", "MAT", "WI"),
                    new JdbcDepartmentLookup(database).findCodes());
        } finally {
            Files.deleteIfExists(file);
        }
    }

    @Test
    void catalogAlsoWorksWithInMemoryRepository() {
        var lena = new Student(1L, "Lena", "Hoffmann", "lena@example.org", "M2023001",
                LocalDate.of(2023, 10, 1));
        var catalog = new StudentCatalog(new InMemoryStudentRepository(List.of(lena)));

        assertEquals(List.of(lena), catalog.listStudents());
        assertEquals(lena, catalog.findStudent(1).orElseThrow());
    }

    @Test
    void jdbcAdapterTranslatesSqlException() {
        var database = new Database("jdbc:invalid:broken", Path.of("missing"), Path.of("missing"));
        var exception = assertThrows(
                RepositoryException.class,
                () -> new JdbcStudentRepository(database).findAll());
        assertInstanceOf(SQLException.class, exception.getCause());
    }

    private Database database(Path file) {
        return new Database(
                "jdbc:sqlite:" + file,
                Path.of("../sql/schema.sql"),
                Path.of("../sql/seed.sql"));
    }
}
