package course.bikerental;

import java.util.List;
import java.util.Optional;

public interface StationRepository {
  Optional<Station> findById(long id);

  List<Station> findAll();

  Station insert(Station value);
}
