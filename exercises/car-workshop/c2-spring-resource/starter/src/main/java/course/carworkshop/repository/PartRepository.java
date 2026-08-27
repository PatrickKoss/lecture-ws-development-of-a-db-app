package course.carworkshop.repository;

import course.carworkshop.domain.Part;
import java.util.List;
import java.util.Optional;

public interface PartRepository {
  List<Part> findAll();

  Optional<Part> findById(long id);

  Part insert(Part value);

  boolean existsByPartNumber(String partNumber);
}
