package course.parceldelivery;

import java.util.List;
import java.util.Optional;

public interface ParcelRepository {
  Optional<Parcel> findById(long id);

  List<Parcel> findAll();

  Parcel insert(Parcel value);
}
