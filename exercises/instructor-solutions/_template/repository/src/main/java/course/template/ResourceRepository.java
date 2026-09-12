package course.template;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository {
  Optional<Resource> findById(long id);

  List<Resource> findAll();

  Resource insert(Resource value);
}
