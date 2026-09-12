package course.library;
import java.util.List;
public final class BookCatalog {
    private final BookRepository repository;
    public BookCatalog(BookRepository repository) { this.repository = repository; }
    public List<Book> list() { return repository.findAll(); }
}
