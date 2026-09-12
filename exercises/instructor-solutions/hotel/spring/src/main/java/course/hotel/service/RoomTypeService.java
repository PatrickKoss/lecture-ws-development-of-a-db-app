package course.hotel.service;

import course.hotel.domain.RoomType;
import course.hotel.repository.RoomTypeRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RoomTypeService {
  private final RoomTypeRepository repository;

  public RoomTypeService(RoomTypeRepository repository) {
    this.repository = repository;
  }

  public List<RoomType> findAll() {
    return repository.findAll();
  }

  public RoomType findById(long id) {
    return repository
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("ROOM_TYPE_NOT_FOUND", "Zimmertyp nicht gefunden"));
  }

  @Transactional
  public RoomType create(RoomTypeCommand command) {
    if (repository.existsByTypeCode(command.typeCode())) {
      throw duplicate();
    }
    return repository.save(
        new RoomType(
            null,
            command.typeCode(),
            command.name(),
            command.capacity(),
            command.standardPriceCents()));
  }

  @Transactional
  public RoomType replace(long id, RoomTypeCommand command) {
    findById(id);
    if (repository.existsByTypeCodeAndIdNot(command.typeCode(), id)) {
      throw duplicate();
    }
    return repository.save(
        new RoomType(
            id,
            command.typeCode(),
            command.name(),
            command.capacity(),
            command.standardPriceCents()));
  }

  @Transactional
  public void delete(long id) {
    repository.deleteById(id);
  }

  private ResourceConflictException duplicate() {
    return new ResourceConflictException("ROOM_TYPE_CODE_EXISTS", "type_code ist bereits vergeben");
  }
}
