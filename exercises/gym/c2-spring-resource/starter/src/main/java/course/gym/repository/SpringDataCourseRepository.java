package course.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataCourseRepository extends JpaRepository<CourseJpaEntity, Long> {
  boolean existsByCourseCode(String value);

  boolean existsByCourseCodeAndIdNot(String value, long id);
}
