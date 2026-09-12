package course.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataMovieRepository extends JpaRepository<MovieJpaEntity, Long> {
  boolean existsByMovieCode(String value);

  boolean existsByMovieCodeAndIdNot(String value, long id);
}
