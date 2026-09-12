package course.musicschool.repository;

import course.musicschool.domain.MusicCourse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "music_courses")
class MusicCourseJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "course_code", nullable = false)
  private String courseCode;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "fee", nullable = false)
  private BigDecimal fee;

  protected MusicCourseJpaEntity() {}

  private MusicCourseJpaEntity(Long id, String courseCode, String title, BigDecimal fee) {
    this.id = id;
    this.courseCode = courseCode;
    this.title = title;
    this.fee = fee;
  }

  static MusicCourseJpaEntity fromDomain(MusicCourse value) {
    return new MusicCourseJpaEntity(value.id(), value.courseCode(), value.title(), value.fee());
  }

  MusicCourse toDomain() {
    return new MusicCourse(id, courseCode, title, fee);
  }

  public Long id() {
    return id;
  }

  public String courseCode() {
    return courseCode;
  }

  public String title() {
    return title;
  }

  public BigDecimal fee() {
    return fee;
  }
}
