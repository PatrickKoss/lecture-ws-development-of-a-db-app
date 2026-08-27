package course.hotel;
import java.sql.SQLException;
import java.util.List;
public final class RoomTypeCatalog {
    private final RoomTypeRepository repository;
    public RoomTypeCatalog(RoomTypeRepository repository) { this.repository = repository; }
    public List<RoomType> list() throws SQLException { return repository.findAll(); }
}
