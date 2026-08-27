package course.hotel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RoomTypeRepository {
  Optional<RoomType> findById(long id) throws SQLException;

  List<RoomType> findAll() throws SQLException;
}
