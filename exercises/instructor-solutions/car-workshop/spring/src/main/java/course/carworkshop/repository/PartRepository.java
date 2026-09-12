package course.carworkshop.repository;

import course.carworkshop.domain.Part;
import java.util.List;
import java.util.Optional;

public interface PartRepository {
  List<Part> findAll();

  Optional<Part> findById(long id);

  Part save(Part value);

  boolean existsByPartNumber(String value);

  boolean existsByPartNumberAndIdNot(String value, long id);

  void deleteById(long id);
}
