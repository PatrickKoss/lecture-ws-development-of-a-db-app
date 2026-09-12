package course.gym.service;

public final class ResourceNotFoundException extends RuntimeException {
  private final String code;

  public ResourceNotFoundException(String code, String message) {
    super(message);
    this.code = code;
  }

  public String code() {
    return code;
  }
}
