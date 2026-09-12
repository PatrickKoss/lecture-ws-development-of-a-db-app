package course.carworkshop.repository;

import course.carworkshop.domain.Part;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class JpaPartRepository implements PartRepository {
  private final SpringDataPartRepository data;

  public JpaPartRepository(SpringDataPartRepository data) {
    this.data = data;
  }

  @Override
  public List<Part> findAll() {
    throw new UnsupportedOperationException("TODO C2: findAll über Spring Data implementieren");
  }

  @Override
  public Optional<Part> findById(long id) {
    throw new UnsupportedOperationException("TODO C2: findById über Spring Data implementieren");
  }

  @Override
  public Part save(Part value) {
    throw new UnsupportedOperationException(
        "TODO C3: mit saveAndFlush speichern und Constraints übersetzen");
  }

  @Override
  public boolean existsByPartNumber(String value) {
    throw new UnsupportedOperationException("TODO C3: abgeleitete Exists-Methode verwenden");
  }

  @Override
  public boolean existsByPartNumberAndIdNot(String value, long id) {
    throw new UnsupportedOperationException(
        "TODO Vertiefung: Konfliktprüfung für PUT implementieren");
  }

  @Override
  public void deleteById(long id) {
    throw new UnsupportedOperationException(
        "TODO Vertiefung: idempotentes DELETE mit flush implementieren");
  }
}
