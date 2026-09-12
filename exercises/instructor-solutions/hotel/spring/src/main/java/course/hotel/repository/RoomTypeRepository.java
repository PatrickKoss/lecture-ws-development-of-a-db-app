package course.hotel.repository;

import course.hotel.domain.RoomType;
import java.util.List;
import java.util.Optional;

public interface RoomTypeRepository {
  List<RoomType> findAll();

  Optional<RoomType> findById(long id);

  RoomType save(RoomType value);

  boolean existsByTypeCode(String value);

  boolean existsByTypeCodeAndIdNot(String value, long id);

  void deleteById(long id);
}
