package course.template.service;

import course.template.api.CreateResourceRequest;
import course.template.domain.Resource;
import course.template.repository.ResourceRepository;
import course.template.web.ConflictException;
import course.template.web.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
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
            () -> new NotFoundException("RESOURCE_NOT_FOUND", "Hauptressource nicht gefunden"));
  }

  public Resource create(CreateResourceRequest request) {
    if (repository.existsByResourceCode(request.resourceCode()))
      throw new ConflictException(
          "RESOURCE_RESOURCE_CODE_EXISTS",
          "Hauptressource mit diesem Wert für resource_code existiert bereits");
    return repository.insert(
        new Resource(null, request.resourceCode(), request.name(), request.measure()));
  }
}
