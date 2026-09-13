package org.lecture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class JdbcStudentRepositoryTest {
    @Disabled("TODO B3: implement findAll and map")
    @Test
    void readsStudentsInIdOrder() throws Exception {
        withRepository(repository -> {
            assertEquals(20, repository.findAll().size());
            assertEquals("M2023001", repository.findAll().getFirst().studentNumber());
        });
    }

    @Disabled("TODO B3: implement findById")
    @Test
    void readsKnownStudentAndReportsMissingId() throws Exception {
        withRepository(repository -> {
            assertEquals("Lena", repository.findById(1).orElseThrow().firstName());
            assertTrue(repository.findById(99999).isEmpty());
        });
    }

    private void withRepository(CheckedConsumer action) throws Exception {
        var file = Files.createTempFile("university-b3-", ".db");
        try {
            var database = new Database("jdbc:sqlite:" + file);
            database.initialize();
            action.accept(new JdbcStudentRepository(database));
        } finally {
            Files.deleteIfExists(file);
        }
    }

    private interface CheckedConsumer {
        void accept(JdbcStudentRepository repository) throws Exception;
    }
}
