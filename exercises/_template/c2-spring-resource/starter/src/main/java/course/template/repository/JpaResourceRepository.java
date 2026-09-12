package course.template.repository;

import course.template.domain.Resource;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class JpaResourceRepository implements ResourceRepository {
  private final SpringDataResourceRepository data;

  public JpaResourceRepository(SpringDataResourceRepository data) {
    this.data = data;
  }

  @Override
  public List<Resource> findAll() {
    throw new UnsupportedOperationException("TODO C2: findAll über Spring Data implementieren");
  }

  @Override
  public Optional<Resource> findById(long id) {
    throw new UnsupportedOperationException("TODO C2: findById über Spring Data implementieren");
  }

  @Override
  public Resource save(Resource value) {
    throw new UnsupportedOperationException(
        "TODO C3: mit saveAndFlush speichern und Constraints übersetzen");
  }

  @Override
  public boolean existsByResourceCode(String value) {
    throw new UnsupportedOperationException("TODO C3: abgeleitete Exists-Methode verwenden");
  }

  @Override
  public boolean existsByResourceCodeAndIdNot(String value, long id) {
    throw new UnsupportedOperationException(
        "TODO Vertiefung: Konfliktprüfung für PUT implementieren");
  }

  @Override
  public void deleteById(long id) {
    throw new UnsupportedOperationException(
        "TODO Vertiefung: idempotentes DELETE mit flush implementieren");
  }
}
