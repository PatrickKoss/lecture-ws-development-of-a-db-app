package course.gym.api;

import course.gym.service.CourseService;
import course.gym.web.ApiError;
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
public class CourseController {
  private final CourseService service;

  public CourseController(CourseService service) {
    this.service = service;
  }

  @Operation(summary = "Alle Kurse lesen", operationId = "listCourses")
  @ApiResponse(
      responseCode = "200",
      description = "Liste aller Kurse",
      content =
          @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = CourseResponse.class))))
  @GetMapping
  public List<CourseResponse> findAll() {
    return service.findAll().stream().map(CourseResponse::from).toList();
  }

  @Operation(summary = "Kurs lesen", operationId = "getCourse")
  @ApiResponse(
      responseCode = "200",
      description = "Kurs gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = CourseResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Kurs nicht gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @GetMapping("/{id}")
  public CourseResponse findById(@PathVariable long id) {
    return CourseResponse.from(service.findById(id));
  }

  @Operation(
      summary = "Kurs anlegen",
      operationId = "createCourse",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = CreateCourseRequest.class))))
  @ApiResponse(
      responseCode = "201",
      description = "Kurs angelegt",
      headers =
          @Header(
              name = "Location",
              description = "URL der angelegten Ressource",
              schema = @Schema(type = "string", format = "uri")),
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = CourseResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Eingabe verletzt die Validierung",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "409",
      description = "courseCode darf nicht doppelt vorkommen",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @PostMapping
  public ResponseEntity<CourseResponse> create(@Valid @RequestBody CreateCourseRequest request) {
    var response = CourseResponse.from(service.create(request));
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }
}
