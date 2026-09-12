package course.template.repository;

import course.template.domain.Resource;
import java.util.List;
import java.util.Optional;

public interface ResourceRepository {
  List<Resource> findAll();

  Optional<Resource> findById(long id);

  Resource save(Resource value);

  boolean existsByResourceCode(String value);

  boolean existsByResourceCodeAndIdNot(String value, long id);

  void deleteById(long id);
}
