package course.bikerental.service;

import course.bikerental.api.CreateStationRequest;
import course.bikerental.domain.Station;
import course.bikerental.repository.StationRepository;
import course.bikerental.web.ConflictException;
import course.bikerental.web.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
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
        .orElseThrow(() -> new NotFoundException("STATION_NOT_FOUND", "Station nicht gefunden"));
  }

  public Station create(CreateStationRequest request) {
    if (repository.existsByStationCode(request.stationCode()))
      throw new ConflictException(
          "STATION_STATION_CODE_EXISTS",
          "Station mit diesem Wert für station_code existiert bereits");
    return repository.insert(
        new Station(
            null,
            request.stationCode(),
            request.name(),
            request.address(),
            request.capacity(),
            request.status()));
  }
}
