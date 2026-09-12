package course.museum;

import java.util.List;
import java.util.Optional;

public interface ExhibitRepository {
  Optional<Exhibit> findById(long id);

  List<Exhibit> findAll();

  Exhibit insert(Exhibit value);
}
