package com.example.restsimple;

import static org.assertj.core.api.Assertions.assertThat;
import com.example.restsimple.repository.StudentRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaRepositoryExerciseTest {
    @Autowired StudentRepository repository;

    @Disabled("TODO C2: nach findAll aktivieren")
    @Test void readsSortedSeedRows() {
        assertThat(repository.findAll()).hasSize(20).extracting(value -> value.id()).isSorted();
    }

    @Disabled("TODO C2: nach findById aktivieren")
    @Test void readsKnownIdAndReportsMissingId() {
        assertThat(repository.findById(1)).isPresent();
        assertThat(repository.findById(Long.MAX_VALUE)).isEmpty();
    }
}
