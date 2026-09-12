package course.parceldelivery.repository;

import course.parceldelivery.domain.Parcel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaParcelRepository implements ParcelRepository {
  private final SpringDataParcelRepository data;

  public JpaParcelRepository(SpringDataParcelRepository data) {
    this.data = data;
  }

  @Override
  public List<Parcel> findAll() {
    return data.findAll(Sort.by("id")).stream().map(ParcelJpaEntity::toDomain).toList();
  }

  @Override
  public Optional<Parcel> findById(long id) {
    return data.findById(id).map(ParcelJpaEntity::toDomain);
  }

  @Override
  public Parcel save(Parcel value) {
    try {
      return data.saveAndFlush(ParcelJpaEntity.fromDomain(value)).toDomain();
    } catch (RuntimeException error) {
      throw SQLiteConstraintTranslator.translate(error);
    }
  }

  @Override
  public boolean existsByTrackingCode(String value) {
    return data.existsByTrackingCode(value);
  }

  @Override
  public boolean existsByTrackingCodeAndIdNot(String value, long id) {
    return data.existsByTrackingCodeAndIdNot(value, id);
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
