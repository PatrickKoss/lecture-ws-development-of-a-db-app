package course.library;
import java.sql.SQLException;
import java.util.List;
public final class BookCatalog {
    private final BookRepository repository;
    public BookCatalog(BookRepository repository) { this.repository = repository; }
    public List<Book> list() throws SQLException { return repository.findAll(); }
}
