package course.hotel.service;

import course.hotel.api.CreateRoomTypeRequest;
import course.hotel.domain.RoomType;
import course.hotel.repository.RoomTypeRepository;
import course.hotel.web.ConflictException;
import course.hotel.web.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
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
        .orElseThrow(() -> new NotFoundException("ROOMTYPE_NOT_FOUND", "Zimmertyp nicht gefunden"));
  }

  public RoomType create(CreateRoomTypeRequest request) {
    if (repository.existsByTypeCode(request.typeCode()))
      throw new ConflictException(
          "ROOMTYPE_TYPE_CODE_EXISTS", "Zimmertyp mit diesem Wert für type_code existiert bereits");
    return repository.insert(
        new RoomType(
            null,
            request.typeCode(),
            request.name(),
            request.capacity(),
            request.standardPriceCents()));
  }
}
