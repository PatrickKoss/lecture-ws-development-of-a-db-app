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
    return repository.findAll();
  }

  public Resource findById(long id) {
    return repository
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "RESOURCE_NOT_FOUND", "Hauptressource nicht gefunden"));
  }

  @Transactional
  public Resource create(ResourceCommand command) {
    if (repository.existsByResourceCode(command.resourceCode())) {
      throw duplicate();
    }
    return repository.save(
        new Resource(null, command.resourceCode(), command.name(), command.measure()));
  }

  @Transactional
  public Resource replace(long id, ResourceCommand command) {
    findById(id);
    if (repository.existsByResourceCodeAndIdNot(command.resourceCode(), id)) {
      throw duplicate();
    }
    return repository.save(
        new Resource(id, command.resourceCode(), command.name(), command.measure()));
  }

  @Transactional
  public void delete(long id) {
    repository.deleteById(id);
  }

  private ResourceConflictException duplicate() {
    return new ResourceConflictException(
        "RESOURCE_CODE_EXISTS", "resource_code ist bereits vergeben");
  }
}
