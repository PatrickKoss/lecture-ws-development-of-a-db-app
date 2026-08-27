package course.foodmarketplace.service;

import course.foodmarketplace.api.CreateRestaurantRequest;
import course.foodmarketplace.domain.Restaurant;
import course.foodmarketplace.repository.RestaurantRepository;
import course.foodmarketplace.web.ConflictException;
import course.foodmarketplace.web.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
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
            () -> new NotFoundException("RESTAURANT_NOT_FOUND", "Restaurant nicht gefunden"));
  }

  public Restaurant create(CreateRestaurantRequest request) {
    if (repository.existsByPartnerNumber(request.partnerNumber()))
      throw new ConflictException(
          "RESTAURANT_PARTNER_NUMBER_EXISTS",
          "Restaurant mit diesem Wert für partner_number existiert bereits");
    return repository.insert(
        new Restaurant(
            null,
            request.partnerNumber(),
            request.name(),
            request.street(),
            request.postalCode(),
            request.city(),
            request.commissionRate(),
            request.active()));
  }
}
