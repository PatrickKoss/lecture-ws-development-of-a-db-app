package course.cinema.repository;

import course.cinema.domain.Movie;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movies")
class MovieJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "movie_code", nullable = false)
  private String movieCode;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "release_year", nullable = false)
  private Integer releaseYear;

  @Column(name = "duration_minutes", nullable = false)
  private Integer durationMinutes;

  @Column(name = "fsk_code", nullable = false)
  private String fskCode;

  @Column(name = "minimum_age", nullable = false)
  private Integer minimumAge;

  protected MovieJpaEntity() {}

  private MovieJpaEntity(
      Long id,
      String movieCode,
      String title,
      Integer releaseYear,
      Integer durationMinutes,
      String fskCode,
      Integer minimumAge) {
    this.id = id;
    this.movieCode = movieCode;
    this.title = title;
    this.releaseYear = releaseYear;
    this.durationMinutes = durationMinutes;
    this.fskCode = fskCode;
    this.minimumAge = minimumAge;
  }

  static MovieJpaEntity fromDomain(Movie value) {
    return new MovieJpaEntity(
        value.id(),
        value.movieCode(),
        value.title(),
        value.releaseYear(),
        value.durationMinutes(),
        value.fskCode(),
        value.minimumAge());
  }

  Movie toDomain() {
    return new Movie(id, movieCode, title, releaseYear, durationMinutes, fskCode, minimumAge);
  }

  public Long id() {
    return id;
  }

  public String movieCode() {
    return movieCode;
  }

  public String title() {
    return title;
  }

  public Integer releaseYear() {
    return releaseYear;
  }

  public Integer durationMinutes() {
    return durationMinutes;
  }

  public String fskCode() {
    return fskCode;
  }

  public Integer minimumAge() {
    return minimumAge;
  }
}
