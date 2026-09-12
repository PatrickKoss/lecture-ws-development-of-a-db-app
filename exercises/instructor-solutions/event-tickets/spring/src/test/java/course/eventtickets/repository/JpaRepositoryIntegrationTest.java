package course.eventtickets.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import course.eventtickets.domain.Venue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaRepositoryIntegrationTest {
  @Autowired VenueRepository repository;
  @Autowired LookupRepository lookups;

  @Test
  void readsSeedRowsThroughHibernateMappings() {
    assertThat(repository.findById(1)).isPresent();
    assertThat(repository.findAll()).isNotEmpty();
    assertThat(lookups.findAllLabels())
        .containsExactly(
            "Ruhrpott Events",
            "Klangwerk West",
            "Lesebühne Ruhr",
            "Förderturm Kultur",
            "Revierklang GmbH",
            "Wortwechsel West",
            "Kanallicht Kultur",
            "Stahlstadt Konzerte",
            "Kapitel Zwei Veranstaltungen",
            "Nordstern Booking");
  }

  @Test
  void translatesARealSQLiteConstraintAtThePersistenceBoundary() {
    var duplicate = new Venue(null, "V-01", "Testhalle", "Testweg 1", "45127", "Essen", 300);
    assertThatThrownBy(() -> repository.save(duplicate))
        .isInstanceOf(PersistenceConstraintException.class);
  }
}
