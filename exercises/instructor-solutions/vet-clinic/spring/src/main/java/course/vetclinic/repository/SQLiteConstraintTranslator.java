package course.vetclinic.repository;

final class SQLiteConstraintTranslator {
  private SQLiteConstraintTranslator() {}

  static RuntimeException translate(RuntimeException error) {
    for (Throwable cause = error; cause != null; cause = cause.getCause()) {
      if (cause instanceof java.sql.SQLException sql && (sql.getErrorCode() & 0xff) == 19) {
        return new PersistenceConstraintException(error);
      }
    }
    return error;
  }
}
