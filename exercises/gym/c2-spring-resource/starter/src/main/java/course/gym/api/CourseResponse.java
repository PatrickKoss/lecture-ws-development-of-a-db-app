package course.gym.api;

import course.gym.domain.Course;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Öffentliche Darstellung von Kurse")
public record CourseResponse() {
  // TODO C1: Ergänzt die Response-Felder aus der Feldspezifikation im Aufgabenblatt.
  public static CourseResponse from(Course value) {
    throw new UnsupportedOperationException("TODO C2: Domainobjekt in Response umwandeln");
  }
}
