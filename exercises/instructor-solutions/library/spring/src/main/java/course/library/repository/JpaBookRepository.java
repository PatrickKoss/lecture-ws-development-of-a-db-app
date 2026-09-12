package course.library.repository;

import course.library.domain.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaBookRepository implements BookRepository {
  private final SpringDataBookRepository data;

  public JpaBookRepository(SpringDataBookRepository data) {
    this.data = data;
  }

  @Override
  public List<Book> findAll() {
    return data.findAll(Sort.by("id")).stream().map(BookJpaEntity::toDomain).toList();
  }

  @Override
  public Optional<Book> findById(long id) {
    return data.findById(id).map(BookJpaEntity::toDomain);
  }

  @Override
  public Book save(Book value) {
    try {
      return data.saveAndFlush(BookJpaEntity.fromDomain(value)).toDomain();
    } catch (RuntimeException error) {
      throw SQLiteConstraintTranslator.translate(error);
    }
  }

  @Override
  public boolean existsByIsbn(String value) {
    return data.existsByIsbn(value);
  }

  @Override
  public boolean existsByIsbnAndIdNot(String value, long id) {
    return data.existsByIsbnAndIdNot(value, id);
  }

  @Override
  public void deleteById(long id) {
    try {
      data.findById(id)
          .ifPresent(
              found -> {
                data.delete(found);
                data.flush();
              });
    } catch (RuntimeException error) {
      throw SQLiteConstraintTranslator.translate(error);
    }
  }
}
