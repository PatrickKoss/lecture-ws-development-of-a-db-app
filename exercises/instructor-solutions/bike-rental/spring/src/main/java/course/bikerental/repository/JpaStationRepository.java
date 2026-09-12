package course.bikerental.repository;

import course.bikerental.domain.Station;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaStationRepository implements StationRepository {
  private final SpringDataStationRepository data;

  public JpaStationRepository(SpringDataStationRepository data) {
    this.data = data;
  }

  @Override
  public List<Station> findAll() {
    return data.findAll(Sort.by("id")).stream().map(StationJpaEntity::toDomain).toList();
  }

  @Override
  public Optional<Station> findById(long id) {
    return data.findById(id).map(StationJpaEntity::toDomain);
  }

  @Override
  public Station save(Station value) {
    try {
      return data.saveAndFlush(StationJpaEntity.fromDomain(value)).toDomain();
    } catch (RuntimeException error) {
      throw SQLiteConstraintTranslator.translate(error);
    }
  }

  @Override
  public boolean existsByStationCode(String value) {
    return data.existsByStationCode(value);
  }

  @Override
  public boolean existsByStationCodeAndIdNot(String value, long id) {
    return data.existsByStationCodeAndIdNot(value, id);
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
