package course.carworkshop.service;

import course.carworkshop.domain.Part;
import course.carworkshop.repository.PartRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PartService {
  private final PartRepository repository;

  public PartService(PartRepository repository) {
    this.repository = repository;
  }

  public List<Part> findAll() {
    return repository.findAll();
  }

  public Part findById(long id) {
    return repository
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("PART_NOT_FOUND", "Ersatzteil nicht gefunden"));
  }

  @Transactional
  public Part create(PartCommand command) {
    if (repository.existsByPartNumber(command.partNumber())) {
      throw duplicate();
    }
    return repository.save(
        new Part(
            null,
            command.partNumber(),
            command.name(),
            command.category(),
            command.shelfCode(),
            command.stockQuantity(),
            command.reorderLevel(),
            command.listPrice()));
  }

  @Transactional
  public Part replace(long id, PartCommand command) {
    findById(id);
    if (repository.existsByPartNumberAndIdNot(command.partNumber(), id)) {
      throw duplicate();
    }
    return repository.save(
        new Part(
            id,
            command.partNumber(),
            command.name(),
            command.category(),
            command.shelfCode(),
            command.stockQuantity(),
            command.reorderLevel(),
            command.listPrice()));
  }

  @Transactional
  public void delete(long id) {
    repository.deleteById(id);
  }

  private ResourceConflictException duplicate() {
    return new ResourceConflictException("PART_NUMBER_EXISTS", "part_number ist bereits vergeben");
  }
}
