package course.vetclinic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryMedicationRepository implements MedicationRepository {
  private final List<Medication> values = new ArrayList<>();
  private long nextId = 1;

  public InMemoryMedicationRepository() {}

  public InMemoryMedicationRepository(List<Medication> initialValues) {
    for (Medication value : initialValues) {
      if (value.id() == null) {
        throw new IllegalArgumentException("Initial values need an ID");
      }
      if (findById(value.id()).isPresent()) {
        throw new IllegalArgumentException("Duplicate initial ID " + value.id());
      }
      rejectDuplicateKey(value);
      values.add(value);
      nextId = Math.max(nextId, value.id() + 1);
    }
  }

  @Override
  public synchronized Optional<Medication> findById(long id) {
    return values.stream().filter(value -> value.id() == id).findFirst();
  }

  @Override
  public synchronized List<Medication> findAll() {
    return values.stream().sorted(Comparator.comparing(Medication::id)).toList();
  }

  @Override
  public synchronized Medication insert(Medication value) {
    Objects.requireNonNull(value, "value");
    rejectDuplicateKey(value);
    var inserted = withId(value, nextId++);
    values.add(inserted);
    return inserted;
  }

  private void rejectDuplicateKey(Medication candidate) {
    boolean duplicate =
        values.stream().anyMatch(value -> Objects.equals(value.pzn(), candidate.pzn()));
    if (duplicate) {
      throw new DuplicateKeyException("The pzn is already in use");
    }
  }

  private Medication withId(Medication value, long id) {
    return new Medication(
        id,
        value.pzn(),
        value.productName(),
        value.activeIngredient(),
        value.dosageForm(),
        value.prescriptionRequired(),
        value.active());
  }
}
