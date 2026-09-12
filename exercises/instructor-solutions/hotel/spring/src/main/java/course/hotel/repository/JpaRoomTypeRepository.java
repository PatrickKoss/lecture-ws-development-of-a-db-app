package course.hotel.repository;

import course.hotel.domain.RoomType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaRoomTypeRepository implements RoomTypeRepository {
  private final SpringDataRoomTypeRepository data;

  public JpaRoomTypeRepository(SpringDataRoomTypeRepository data) {
    this.data = data;
  }

  @Override
  public List<RoomType> findAll() {
    return data.findAll(Sort.by("id")).stream().map(RoomTypeJpaEntity::toDomain).toList();
  }

  @Override
  public Optional<RoomType> findById(long id) {
    return data.findById(id).map(RoomTypeJpaEntity::toDomain);
  }

  @Override
  public RoomType save(RoomType value) {
    try {
      return data.saveAndFlush(RoomTypeJpaEntity.fromDomain(value)).toDomain();
    } catch (RuntimeException error) {
      throw SQLiteConstraintTranslator.translate(error);
    }
  }

  @Override
  public boolean existsByTypeCode(String value) {
    return data.existsByTypeCode(value);
  }

  @Override
  public boolean existsByTypeCodeAndIdNot(String value, long id) {
    return data.existsByTypeCodeAndIdNot(value, id);
  }

  @Override
  public void deleteById(long id) {
    try {
      data.findById(id)
          .ifPresent(
              found -> {
                data.delete(found);
                data.flush();
              });
    } catch (RuntimeException error) {
      throw SQLiteConstraintTranslator.translate(error);
    }
  }
}
