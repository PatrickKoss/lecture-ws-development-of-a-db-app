package com.example.restsimple.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import com.example.restsimple.domain.Student;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaStudentRepositoryTest {
    @Autowired StudentRepository repository;

    @Test void translatesDatabaseConstraintsWhenServiceChecksAreBypassed() {
        var duplicate = new Student(null, "Andere", "Person", "andere@campus.example",
                "M2023001", LocalDate.of(2026, 4, 1));
        assertThatThrownBy(() -> repository.save(duplicate))
                .isInstanceOf(PersistenceConstraintException.class);
    }
}
