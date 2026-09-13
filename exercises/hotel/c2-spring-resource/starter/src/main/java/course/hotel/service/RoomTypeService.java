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
    throw new UnsupportedOperationException("TODO C2: Alle Datensätze aus dem Repository lesen");
  }

  public RoomType findById(long id) {
    throw new UnsupportedOperationException("TODO C2: Datensatz lesen und unbekannte ID als 404 melden");
  }

  @Transactional
  public RoomType create(RoomTypeCommand command) {
    throw new UnsupportedOperationException("TODO C3: Fachschlüssel prüfen und Datensatz speichern");
  }

}
