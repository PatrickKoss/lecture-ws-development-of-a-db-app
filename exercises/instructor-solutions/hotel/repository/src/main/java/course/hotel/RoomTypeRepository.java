package course.hotel;

import java.util.List;
import java.util.Optional;

public interface RoomTypeRepository {
  Optional<RoomType> findById(long id);

  List<RoomType> findAll();

  RoomType insert(RoomType value);
}
