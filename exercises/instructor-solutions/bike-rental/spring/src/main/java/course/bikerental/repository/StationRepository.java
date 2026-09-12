package course.bikerental.repository;

import course.bikerental.domain.Station;
import java.util.List;
import java.util.Optional;

public interface StationRepository {
  List<Station> findAll();

  Optional<Station> findById(long id);

  Station save(Station value);

  boolean existsByStationCode(String value);

  boolean existsByStationCodeAndIdNot(String value, long id);

  void deleteById(long id);
}
