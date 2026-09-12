package course.vetclinic.repository;

import java.util.List;

public interface LookupRepository {
  List<String> findAllLabels();
}
