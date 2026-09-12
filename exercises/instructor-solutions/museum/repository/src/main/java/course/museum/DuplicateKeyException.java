package course.museum;

public final class DuplicateKeyException extends RepositoryException {
  public DuplicateKeyException(String message, Throwable cause) {
    super(message, cause);
  }

  public DuplicateKeyException(String message) {
    super(message, null);
  }
}
