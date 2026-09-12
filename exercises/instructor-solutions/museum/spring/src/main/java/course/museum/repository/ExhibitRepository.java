package course.museum.repository;

import course.museum.domain.Exhibit;
import java.util.List;
import java.util.Optional;

public interface ExhibitRepository {
  List<Exhibit> findAll();

  Optional<Exhibit> findById(long id);

  Exhibit save(Exhibit value);

  boolean existsByInventoryCode(String value);

  boolean existsByInventoryCodeAndIdNot(String value, long id);

  void deleteById(long id);
}
