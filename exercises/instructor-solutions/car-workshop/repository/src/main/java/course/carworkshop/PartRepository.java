package course.carworkshop;

import java.util.List;
import java.util.Optional;

public interface PartRepository {
  Optional<Part> findById(long id);

  List<Part> findAll();

  Part insert(Part value);
}
