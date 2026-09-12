package course.library.repository;

import course.library.domain.Book;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
class BookJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "isbn", nullable = false)
  private String isbn;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "publication_year", nullable = false)
  private Integer publicationYear;

  @Column(name = "subject_area", nullable = false)
  private String subjectArea;

  @Column(name = "shelf_code", nullable = false)
  private String shelfCode;

  protected BookJpaEntity() {}

  private BookJpaEntity(
      Long id,
      String isbn,
      String title,
      Integer publicationYear,
      String subjectArea,
      String shelfCode) {
    this.id = id;
    this.isbn = isbn;
    this.title = title;
    this.publicationYear = publicationYear;
    this.subjectArea = subjectArea;
    this.shelfCode = shelfCode;
  }

  static BookJpaEntity fromDomain(Book value) {
    return new BookJpaEntity(
        value.id(),
        value.isbn(),
        value.title(),
        value.publicationYear(),
        value.subjectArea(),
        value.shelfCode());
  }

  Book toDomain() {
    return new Book(id, isbn, title, publicationYear, subjectArea, shelfCode);
  }

  public Long id() {
    return id;
  }

  public String isbn() {
    return isbn;
  }

  public String title() {
    return title;
  }

  public Integer publicationYear() {
    return publicationYear;
  }

  public String subjectArea() {
    return subjectArea;
  }

  public String shelfCode() {
    return shelfCode;
  }
}
