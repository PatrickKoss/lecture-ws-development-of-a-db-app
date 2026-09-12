package course.hotel.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import course.hotel.domain.RoomType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaRepositoryIntegrationTest {
  @Autowired RoomTypeRepository repository;
  @Autowired LookupRepository lookups;

  @Test
  void readsSeedRowsThroughHibernateMappings() {
    assertThat(repository.findById(1)).isPresent();
    assertThat(repository.findAll()).isNotEmpty();
    assertThat(lookups.findAllLabels())
        .containsExactly(
            "Frühstücksbuffet",
            "Tiefgaragenplatz",
            "Spa-Tageskarte",
            "Minibar Klassik",
            "Late Check-out",
            "Wäscheservice",
            "Babybett",
            "Hundepauschale",
            "Flughafentransfer",
            "Konferenzpaket Rhein");
  }

  @Test
  void translatesARealSQLiteConstraintAtThePersistenceBoundary() {
    var duplicate = new RoomType(null, "EZ", "Testzimmer", 2, 14900);
    assertThatThrownBy(() -> repository.save(duplicate))
        .isInstanceOf(PersistenceConstraintException.class);
  }
}
