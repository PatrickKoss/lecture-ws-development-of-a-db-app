package course.museum.service;

import course.museum.domain.Exhibit;
import course.museum.repository.ExhibitRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
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
            () -> new ResourceNotFoundException("EXHIBIT_NOT_FOUND", "Exponat nicht gefunden"));
  }

  @Transactional
  public Exhibit create(ExhibitCommand command) {
    if (repository.existsByInventoryCode(command.inventoryCode())) {
      throw duplicate();
    }
    return repository.save(
        new Exhibit(null, command.inventoryCode(), command.title(), command.insuredValue()));
  }

  @Transactional
  public Exhibit replace(long id, ExhibitCommand command) {
    findById(id);
    if (repository.existsByInventoryCodeAndIdNot(command.inventoryCode(), id)) {
      throw duplicate();
    }
    return repository.save(
        new Exhibit(id, command.inventoryCode(), command.title(), command.insuredValue()));
  }

  @Transactional
  public void delete(long id) {
    repository.deleteById(id);
  }

  private ResourceConflictException duplicate() {
    return new ResourceConflictException(
        "INVENTORY_CODE_EXISTS", "inventory_code ist bereits vergeben");
  }
}
