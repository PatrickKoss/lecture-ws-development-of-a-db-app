package course.parceldelivery.web;

public final class ConflictException extends ApiException {
  public ConflictException(String code, String message) {
    super(code, message);
  }
}
