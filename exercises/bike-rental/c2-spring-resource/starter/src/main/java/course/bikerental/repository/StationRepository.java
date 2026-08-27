package course.bikerental.repository;

import course.bikerental.domain.Station;
import java.util.List;
import java.util.Optional;

public interface StationRepository {
  List<Station> findAll();

  Optional<Station> findById(long id);

  Station insert(Station value);

  boolean existsByStationCode(String stationCode);
}
