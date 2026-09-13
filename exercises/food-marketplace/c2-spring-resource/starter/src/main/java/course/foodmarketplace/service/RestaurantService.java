package course.foodmarketplace.service;

import course.foodmarketplace.domain.Restaurant;
import course.foodmarketplace.repository.RestaurantRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RestaurantService {
  private final RestaurantRepository repository;

  public RestaurantService(RestaurantRepository repository) {
    this.repository = repository;
  }

  public List<Restaurant> findAll() {
    throw new UnsupportedOperationException("TODO C2: Alle Datensätze aus dem Repository lesen");
  }

  public Restaurant findById(long id) {
    throw new UnsupportedOperationException("TODO C2: Datensatz lesen und unbekannte ID als 404 melden");
  }

  @Transactional
  public Restaurant create(RestaurantCommand command) {
    throw new UnsupportedOperationException("TODO C3: Fachschlüssel prüfen und Datensatz speichern");
  }

}
