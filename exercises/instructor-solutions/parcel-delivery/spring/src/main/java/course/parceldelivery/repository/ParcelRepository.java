package course.parceldelivery.repository;

import course.parceldelivery.domain.Parcel;
import java.util.List;
import java.util.Optional;

public interface ParcelRepository {
  List<Parcel> findAll();

  Optional<Parcel> findById(long id);

  Parcel save(Parcel value);

  boolean existsByTrackingCode(String value);

  boolean existsByTrackingCodeAndIdNot(String value, long id);

  void deleteById(long id);
}
