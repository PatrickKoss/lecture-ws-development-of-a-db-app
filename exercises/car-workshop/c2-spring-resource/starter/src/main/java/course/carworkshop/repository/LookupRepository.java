package course.carworkshop.repository;

import java.util.List;

public interface LookupRepository {
  List<String> findAllLabels();
}
