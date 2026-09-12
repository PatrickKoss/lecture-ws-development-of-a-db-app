package course.bikerental.service;

import course.bikerental.domain.Station;
import course.bikerental.repository.StationRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StationService {
  private final StationRepository repository;

  public StationService(StationRepository repository) {
    this.repository = repository;
  }

  public List<Station> findAll() {
    return repository.findAll();
  }

  public Station findById(long id) {
    return repository
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("STATION_NOT_FOUND", "Station nicht gefunden"));
  }

  @Transactional
  public Station create(StationCommand command) {
    if (repository.existsByStationCode(command.stationCode())) {
      throw duplicate();
    }
    return repository.save(
        new Station(
            null,
            command.stationCode(),
            command.name(),
            command.address(),
            command.capacity(),
            command.status()));
  }

  @Transactional
  public Station replace(long id, StationCommand command) {
    findById(id);
    if (repository.existsByStationCodeAndIdNot(command.stationCode(), id)) {
      throw duplicate();
    }
    return repository.save(
        new Station(
            id,
            command.stationCode(),
            command.name(),
            command.address(),
            command.capacity(),
            command.status()));
  }

  @Transactional
  public void delete(long id) {
    repository.deleteById(id);
  }

  private ResourceConflictException duplicate() {
    return new ResourceConflictException(
        "STATION_CODE_EXISTS", "station_code ist bereits vergeben");
  }
}
