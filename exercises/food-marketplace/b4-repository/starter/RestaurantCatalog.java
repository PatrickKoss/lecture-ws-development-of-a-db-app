package course.foodmarketplace;
import java.sql.SQLException;
import java.util.List;
public final class RestaurantCatalog {
    private final RestaurantRepository repository;
    public RestaurantCatalog(RestaurantRepository repository) { this.repository = repository; }
    public List<Restaurant> list() throws SQLException { return repository.findAll(); }
}
