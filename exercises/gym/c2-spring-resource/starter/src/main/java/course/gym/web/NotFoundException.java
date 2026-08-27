package course.gym.web;

public final class NotFoundException extends ApiException {
  public NotFoundException(String code, String message) {
    super(code, message);
  }
}
