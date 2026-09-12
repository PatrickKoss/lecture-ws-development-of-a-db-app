package course.foodmarketplace.repository;

import java.util.List;

public interface LookupRepository {
  List<String> findAllLabels();
}
