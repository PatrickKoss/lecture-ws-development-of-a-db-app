package course.foodmarketplace;
import java.util.List;
public final class RestaurantCatalog {
    private final RestaurantRepository repository;
    public RestaurantCatalog(RestaurantRepository repository) { this.repository = repository; }
    public List<Restaurant> list() { return repository.findAll(); }
}
