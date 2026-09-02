package course.parceldelivery.service;

import course.parceldelivery.api.CreateParcelRequest;
import course.parceldelivery.domain.Parcel;
import course.parceldelivery.repository.ParcelRepository;
import course.parceldelivery.web.ConflictException;
import course.parceldelivery.web.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
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
            () -> new NotFoundException("PARCEL_NOT_FOUND", "Paket nicht gefunden"));
  }

  public Parcel create(CreateParcelRequest request) {
    if (repository.existsByTrackingCode(request.trackingCode()))
      throw new ConflictException(
          "PARCEL_TRACKING_CODE_EXISTS",
          "Paket mit diesem Wert für tracking_code existiert bereits");
    return repository.insert(
        new Parcel(null, request.trackingCode(), request.recipient(), request.weight()));
  }
}
