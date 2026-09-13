package course.musicschool.api;

import course.musicschool.service.MusicCourseCommand;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Eingabe für Kursangebote")
public record CreateMusicCourseRequest() {
  // TODO C1: Ergänzt die Request-Felder und ihre OpenAPI-Beschreibungen.
  // TODO C3: Ergänzt Bean-Validation und bildet den Request auf das Command ab.
  public MusicCourseCommand toCommand() {
    throw new UnsupportedOperationException("TODO C3: Request in Command umwandeln");
  }
}
