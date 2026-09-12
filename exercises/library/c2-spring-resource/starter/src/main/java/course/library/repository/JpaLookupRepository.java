package course.library.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaLookupRepository implements LookupRepository {
  private final SpringDataLookupRepository data;

  public JpaLookupRepository(SpringDataLookupRepository data) {
    this.data = data;
  }

  @Override
  public List<String> findAllLabels() {
    return data.findAll(Sort.by("id")).stream().map(LookupJpaEntity::label).toList();
  }
}
