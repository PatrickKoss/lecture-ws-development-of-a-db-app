package course.museum.repository;

import course.museum.domain.Exhibit;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaExhibitRepository implements ExhibitRepository {
  private final SpringDataExhibitRepository data;

  public JpaExhibitRepository(SpringDataExhibitRepository data) {
    this.data = data;
  }

  @Override
  public List<Exhibit> findAll() {
    return data.findAll(Sort.by("id")).stream().map(ExhibitJpaEntity::toDomain).toList();
  }

  @Override
  public Optional<Exhibit> findById(long id) {
    return data.findById(id).map(ExhibitJpaEntity::toDomain);
  }

  @Override
  public Exhibit save(Exhibit value) {
    try {
      return data.saveAndFlush(ExhibitJpaEntity.fromDomain(value)).toDomain();
    } catch (RuntimeException error) {
      throw SQLiteConstraintTranslator.translate(error);
    }
  }

  @Override
  public boolean existsByInventoryCode(String value) {
    return data.existsByInventoryCode(value);
  }

  @Override
  public boolean existsByInventoryCodeAndIdNot(String value, long id) {
    return data.existsByInventoryCodeAndIdNot(value, id);
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
