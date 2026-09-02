package course.museum.service;

import course.museum.api.CreateExhibitRequest;
import course.museum.domain.Exhibit;
import course.museum.repository.ExhibitRepository;
import course.museum.web.ConflictException;
import course.museum.web.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ExhibitService {
  private final ExhibitRepository repository;

  public ExhibitService(ExhibitRepository repository) {
    this.repository = repository;
  }

  public List<Exhibit> findAll() {
    return repository.findAll();
  }

  public Exhibit findById(long id) {
    return repository
        .findById(id)
        .orElseThrow(
            () -> new NotFoundException("EXHIBIT_NOT_FOUND", "Exponat nicht gefunden"));
  }

  public Exhibit create(CreateExhibitRequest request) {
    if (repository.existsByInventoryCode(request.inventoryCode()))
      throw new ConflictException(
          "EXHIBIT_INVENTORY_CODE_EXISTS",
          "Exponat mit diesem Wert für inventory_code existiert bereits");
    return repository.insert(
        new Exhibit(null, request.inventoryCode(), request.title(), request.insuredValue()));
  }
}
