package course.musicschool.repository;

public final class PersistenceConstraintException extends RuntimeException {
  public PersistenceConstraintException(Throwable cause) {
    super("Database constraint rejected the change", cause);
  }
}
