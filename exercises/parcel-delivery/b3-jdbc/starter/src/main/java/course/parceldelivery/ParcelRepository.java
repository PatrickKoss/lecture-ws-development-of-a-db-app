package course.parceldelivery;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ParcelRepository {
  Optional<Parcel> findById(long id) throws SQLException;

  List<Parcel> findAll() throws SQLException;
}
