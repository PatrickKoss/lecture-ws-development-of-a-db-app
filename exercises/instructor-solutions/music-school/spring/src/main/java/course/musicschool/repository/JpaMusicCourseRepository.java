package course.musicschool.repository;

import course.musicschool.domain.MusicCourse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaMusicCourseRepository implements MusicCourseRepository {
  private final SpringDataMusicCourseRepository data;

  public JpaMusicCourseRepository(SpringDataMusicCourseRepository data) {
    this.data = data;
  }

  @Override
  public List<MusicCourse> findAll() {
    return data.findAll(Sort.by("id")).stream().map(MusicCourseJpaEntity::toDomain).toList();
  }

  @Override
  public Optional<MusicCourse> findById(long id) {
    return data.findById(id).map(MusicCourseJpaEntity::toDomain);
  }

  @Override
  public MusicCourse save(MusicCourse value) {
    try {
      return data.saveAndFlush(MusicCourseJpaEntity.fromDomain(value)).toDomain();
    } catch (RuntimeException error) {
      throw SQLiteConstraintTranslator.translate(error);
    }
  }

  @Override
  public boolean existsByCourseCode(String value) {
    return data.existsByCourseCode(value);
  }

  @Override
  public boolean existsByCourseCodeAndIdNot(String value, long id) {
    return data.existsByCourseCodeAndIdNot(value, id);
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
