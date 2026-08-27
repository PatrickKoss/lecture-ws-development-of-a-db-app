package course.library.web;

public final class ConflictException extends ApiException {
  public ConflictException(String code, String message) {
    super(code, message);
  }
}
