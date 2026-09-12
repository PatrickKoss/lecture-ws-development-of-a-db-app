package course.musicschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataMusicCourseRepository extends JpaRepository<MusicCourseJpaEntity, Long> {
  boolean existsByCourseCode(String value);

  boolean existsByCourseCodeAndIdNot(String value, long id);
}
