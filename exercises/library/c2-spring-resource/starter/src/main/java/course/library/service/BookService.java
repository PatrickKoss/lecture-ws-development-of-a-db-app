package course.library.service;

import course.library.domain.Book;
import course.library.repository.BookRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BookService {
  private final BookRepository repository;

  public BookService(BookRepository repository) {
    this.repository = repository;
  }

  public List<Book> findAll() {
    throw new UnsupportedOperationException("TODO C2: Alle Datensätze aus dem Repository lesen");
  }

  public Book findById(long id) {
    throw new UnsupportedOperationException("TODO C2: Datensatz lesen und unbekannte ID als 404 melden");
  }

  @Transactional
  public Book create(BookCommand command) {
    throw new UnsupportedOperationException("TODO C3: Fachschlüssel prüfen und Datensatz speichern");
  }

}
