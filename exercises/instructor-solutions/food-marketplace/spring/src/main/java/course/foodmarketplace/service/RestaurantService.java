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
    return repository.findAll();
  }

  public Restaurant findById(long id) {
    return repository
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException("RESTAURANT_NOT_FOUND", "Restaurant nicht gefunden"));
  }

  @Transactional
  public Restaurant create(RestaurantCommand command) {
    if (repository.existsByPartnerNumber(command.partnerNumber())) {
      throw duplicate();
    }
    return repository.save(
        new Restaurant(
            null,
            command.partnerNumber(),
            command.name(),
            command.street(),
            command.postalCode(),
            command.city(),
            command.commissionRate(),
            command.active()));
  }

  @Transactional
  public Restaurant replace(long id, RestaurantCommand command) {
    findById(id);
    if (repository.existsByPartnerNumberAndIdNot(command.partnerNumber(), id)) {
      throw duplicate();
    }
    return repository.save(
        new Restaurant(
            id,
            command.partnerNumber(),
            command.name(),
            command.street(),
            command.postalCode(),
            command.city(),
            command.commissionRate(),
            command.active()));
  }

  @Transactional
  public void delete(long id) {
    repository.deleteById(id);
  }

  private ResourceConflictException duplicate() {
    return new ResourceConflictException(
        "PARTNER_NUMBER_EXISTS", "partner_number ist bereits vergeben");
  }
}
