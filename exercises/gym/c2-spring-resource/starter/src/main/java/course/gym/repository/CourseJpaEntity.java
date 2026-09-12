package course.gym.repository;

import course.gym.domain.Course;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
class CourseJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "course_code", nullable = false)
  private String courseCode;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "level", nullable = false)
  private String level;

  @Column(name = "duration_minutes", nullable = false)
  private Integer durationMinutes;

  @Column(name = "room_id", nullable = false)
  private Long roomId;

  protected CourseJpaEntity() {}

  private CourseJpaEntity(
      Long id,
      String courseCode,
      String title,
      String level,
      Integer durationMinutes,
      Long roomId) {
    this.id = id;
    this.courseCode = courseCode;
    this.title = title;
    this.level = level;
    this.durationMinutes = durationMinutes;
    this.roomId = roomId;
  }

  static CourseJpaEntity fromDomain(Course value) {
    return new CourseJpaEntity(
        value.id(),
        value.courseCode(),
        value.title(),
        value.level(),
        value.durationMinutes(),
        value.roomId());
  }

  Course toDomain() {
    return new Course(id, courseCode, title, level, durationMinutes, roomId);
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

  public String level() {
    return level;
  }

  public Integer durationMinutes() {
    return durationMinutes;
  }

  public Long roomId() {
    return roomId;
  }
}
