package course.parceldelivery.repository;

import course.parceldelivery.domain.Parcel;
import java.util.List;
import java.util.Optional;

public interface ParcelRepository {
  List<Parcel> findAll();

  Optional<Parcel> findById(long id);

  Parcel insert(Parcel value);

  boolean existsByTrackingCode(String trackingCode);
}
