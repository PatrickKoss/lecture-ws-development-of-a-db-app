package course.bikerental.repository;

import java.util.List;

public interface LookupRepository {
  List<String> findAllLabels();
}
