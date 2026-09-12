package course.carworkshop.repository;

import course.carworkshop.domain.Part;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaPartRepository implements PartRepository {
  private final SpringDataPartRepository data;

  public JpaPartRepository(SpringDataPartRepository data) {
    this.data = data;
  }

  @Override
  public List<Part> findAll() {
    return data.findAll(Sort.by("id")).stream().map(PartJpaEntity::toDomain).toList();
  }

  @Override
  public Optional<Part> findById(long id) {
    return data.findById(id).map(PartJpaEntity::toDomain);
  }

  @Override
  public Part save(Part value) {
    try {
      return data.saveAndFlush(PartJpaEntity.fromDomain(value)).toDomain();
    } catch (RuntimeException error) {
      throw SQLiteConstraintTranslator.translate(error);
    }
  }

  @Override
  public boolean existsByPartNumber(String value) {
    return data.existsByPartNumber(value);
  }

  @Override
  public boolean existsByPartNumberAndIdNot(String value, long id) {
    return data.existsByPartNumberAndIdNot(value, id);
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
