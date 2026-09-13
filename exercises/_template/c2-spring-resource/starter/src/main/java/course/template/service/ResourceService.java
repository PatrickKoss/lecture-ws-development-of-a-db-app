package course.template.service;

import course.template.domain.Resource;
import course.template.repository.ResourceRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ResourceService {
  private final ResourceRepository repository;

  public ResourceService(ResourceRepository repository) {
    this.repository = repository;
  }

  public List<Resource> findAll() {
    throw new UnsupportedOperationException("TODO C2: Alle Datensätze aus dem Repository lesen");
  }

  public Resource findById(long id) {
    throw new UnsupportedOperationException("TODO C2: Datensatz lesen und unbekannte ID als 404 melden");
  }

  @Transactional
  public Resource create(ResourceCommand command) {
    throw new UnsupportedOperationException("TODO C3: Fachschlüssel prüfen und Datensatz speichern");
  }

}
