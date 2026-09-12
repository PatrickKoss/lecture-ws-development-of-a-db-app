package course.musicschool.api;

import course.musicschool.service.MusicCourseService;
import course.musicschool.web.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/courses")
public class MusicCourseController {
  private final MusicCourseService service;

  public MusicCourseController(MusicCourseService service) {
    this.service = service;
  }

  @Operation(summary = "Alle Kursangebote lesen", operationId = "listMusicCourses")
  @ApiResponse(
      responseCode = "200",
      description = "Liste aller Kursangebote",
      content =
          @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = MusicCourseResponse.class))))
  @GetMapping
  public List<MusicCourseResponse> findAll() {
    return service.findAll().stream().map(MusicCourseResponse::from).toList();
  }

  @Operation(summary = "Ein Kursangebot lesen", operationId = "getMusicCourse")
  @ApiResponse(
      responseCode = "200",
      description = "Kursangebot gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = MusicCourseResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Kursangebot nicht gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @GetMapping("/{id}")
  public MusicCourseResponse findById(@PathVariable long id) {
    return MusicCourseResponse.from(service.findById(id));
  }

  @Operation(
      summary = "Ein Kursangebot anlegen",
      operationId = "createMusicCourse",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = CreateMusicCourseRequest.class))))
  @ApiResponse(
      responseCode = "201",
      description = "Kursangebot angelegt",
      headers =
          @Header(
              name = "Location",
              description = "URL des angelegten Kursangebots",
              schema = @Schema(type = "string", format = "uri")),
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = MusicCourseResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Eingabe verletzt die Validierung",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "409",
      description = "Ein Kursangebot mit diesem Kurscode existiert bereits",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @PostMapping
  public ResponseEntity<MusicCourseResponse> create(
      @Valid @RequestBody CreateMusicCourseRequest request) {
    var response = MusicCourseResponse.from(service.create(request));
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }
}
