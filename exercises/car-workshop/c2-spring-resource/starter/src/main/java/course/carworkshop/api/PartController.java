package course.carworkshop.api;

import course.carworkshop.service.PartService;
import course.carworkshop.web.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parts")
public class PartController {
  private final PartService service;

  public PartController(PartService service) {
    this.service = service;
  }

  @GetMapping
  public List<PartResponse> findAll() {
    return service.findAll().stream().map(PartResponse::from).toList();
  }

  @GetMapping("/{id}")
  public PartResponse findById(@PathVariable long id) {
    return PartResponse.from(service.findById(id));
  }

  // Beispiel für C1. Dokumentiert die GET-Methoden nach diesem Muster.
  @Operation(summary = "Part anlegen",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          required = true,
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = CreatePartRequest.class))))
  @ApiResponse(responseCode = "201", description = "Ressource angelegt",
      headers = @Header(name = "Location", description = "URL der neuen Ressource",
          schema = @Schema(type = "string", format = "uri")),
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = PartResponse.class)))
  @ApiResponse(responseCode = "400", description = "Eingabe verletzt die Validierung",
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(responseCode = "409", description = "Eindeutiger Wert bereits vergeben",
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ApiError.class)))
  @PostMapping
  public ResponseEntity<PartResponse> create(@Valid @RequestBody CreatePartRequest request) {
    var response = PartResponse.from(service.create(request));
    var location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(response.id()).toUri();
    return ResponseEntity.created(location).body(response);
  }
}
