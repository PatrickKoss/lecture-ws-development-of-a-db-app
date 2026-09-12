package course.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataBookRepository extends JpaRepository<BookJpaEntity, Long> {
  boolean existsByIsbn(String value);

  boolean existsByIsbnAndIdNot(String value, long id);
}
