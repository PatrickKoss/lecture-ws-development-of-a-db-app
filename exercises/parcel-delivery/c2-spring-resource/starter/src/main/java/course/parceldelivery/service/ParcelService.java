package course.parceldelivery.service;

import course.parceldelivery.domain.Parcel;
import course.parceldelivery.repository.ParcelRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ParcelService {
  private final ParcelRepository repository;

  public ParcelService(ParcelRepository repository) {
    this.repository = repository;
  }

  public List<Parcel> findAll() {
    return repository.findAll();
  }

  public Parcel findById(long id) {
    return repository
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("PARCEL_NOT_FOUND", "Paket nicht gefunden"));
  }

  @Transactional
  public Parcel create(ParcelCommand command) {
    if (repository.existsByTrackingCode(command.trackingCode())) {
      throw duplicate();
    }
    return repository.save(
        new Parcel(null, command.trackingCode(), command.recipient(), command.weight()));
  }

  @Transactional
  public Parcel replace(long id, ParcelCommand command) {
    findById(id);
    if (repository.existsByTrackingCodeAndIdNot(command.trackingCode(), id)) {
      throw duplicate();
    }
    return repository.save(
        new Parcel(id, command.trackingCode(), command.recipient(), command.weight()));
  }

  @Transactional
  public void delete(long id) {
    repository.deleteById(id);
  }

  private ResourceConflictException duplicate() {
    return new ResourceConflictException(
        "TRACKING_CODE_EXISTS", "tracking_code ist bereits vergeben");
  }
}
