package course.vetclinic.repository;

import course.vetclinic.domain.Medication;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaMedicationRepository implements MedicationRepository {
  private final SpringDataMedicationRepository data;

  public JpaMedicationRepository(SpringDataMedicationRepository data) {
    this.data = data;
  }

  @Override
  public List<Medication> findAll() {
    return data.findAll(Sort.by("id")).stream().map(MedicationJpaEntity::toDomain).toList();
  }

  @Override
  public Optional<Medication> findById(long id) {
    return data.findById(id).map(MedicationJpaEntity::toDomain);
  }

  @Override
  public Medication save(Medication value) {
    try {
      return data.saveAndFlush(MedicationJpaEntity.fromDomain(value)).toDomain();
    } catch (RuntimeException error) {
      throw SQLiteConstraintTranslator.translate(error);
    }
  }

  @Override
  public boolean existsByPzn(String value) {
    return data.existsByPzn(value);
  }

  @Override
  public boolean existsByPznAndIdNot(String value, long id) {
    return data.existsByPznAndIdNot(value, id);
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
