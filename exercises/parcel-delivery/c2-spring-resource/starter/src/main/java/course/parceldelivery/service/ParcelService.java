package course.parceldelivery.service;

import course.parceldelivery.domain.Parcel;
import course.parceldelivery.repository.ParcelRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ParcelService {
  private final ParcelRepository repository;

  public ParcelService(ParcelRepository repository) {
    this.repository = repository;
  }

  public List<Parcel> findAll() {
    throw new UnsupportedOperationException("TODO C2: Alle Datensätze aus dem Repository lesen");
  }

  public Parcel findById(long id) {
    throw new UnsupportedOperationException("TODO C2: Datensatz lesen und unbekannte ID als 404 melden");
  }

  @Transactional
  public Parcel create(ParcelCommand command) {
    throw new UnsupportedOperationException("TODO C3: Fachschlüssel prüfen und Datensatz speichern");
  }

}
