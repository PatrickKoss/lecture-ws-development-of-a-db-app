package course.carworkshop.service;

import course.carworkshop.api.CreatePartRequest;
import course.carworkshop.domain.Part;
import course.carworkshop.repository.PartRepository;
import course.carworkshop.web.ConflictException;
import course.carworkshop.web.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
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
        .orElseThrow(() -> new NotFoundException("PART_NOT_FOUND", "Ersatzteil nicht gefunden"));
  }

  public Part create(CreatePartRequest request) {
    if (repository.existsByPartNumber(request.partNumber()))
      throw new ConflictException(
          "PART_PART_NUMBER_EXISTS",
          "Ersatzteil mit diesem Wert für part_number existiert bereits");
    return repository.insert(
        new Part(
            null,
            request.partNumber(),
            request.name(),
            request.category(),
            request.shelfCode(),
            request.stockQuantity(),
            request.reorderLevel(),
            request.listPrice()));
  }
}
