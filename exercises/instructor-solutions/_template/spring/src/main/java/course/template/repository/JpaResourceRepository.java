package course.template.repository;

import course.template.domain.Resource;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaResourceRepository implements ResourceRepository {
  private final SpringDataResourceRepository data;

  public JpaResourceRepository(SpringDataResourceRepository data) {
    this.data = data;
  }

  @Override
  public List<Resource> findAll() {
    return data.findAll(Sort.by("id")).stream().map(ResourceJpaEntity::toDomain).toList();
  }

  @Override
  public Optional<Resource> findById(long id) {
    return data.findById(id).map(ResourceJpaEntity::toDomain);
  }

  @Override
  public Resource save(Resource value) {
    try {
      return data.saveAndFlush(ResourceJpaEntity.fromDomain(value)).toDomain();
    } catch (RuntimeException error) {
      throw SQLiteConstraintTranslator.translate(error);
    }
  }

  @Override
  public boolean existsByResourceCode(String value) {
    return data.existsByResourceCode(value);
  }

  @Override
  public boolean existsByResourceCodeAndIdNot(String value, long id) {
    return data.existsByResourceCodeAndIdNot(value, id);
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
