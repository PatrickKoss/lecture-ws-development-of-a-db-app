package course.parceldelivery.api;

import course.parceldelivery.service.ParcelService;
import course.parceldelivery.web.ApiError;
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
@RequestMapping("/api/parcels")
public class ParcelController {
  private final ParcelService service;

  public ParcelController(ParcelService service) {
    this.service = service;
  }

  @GetMapping
  public List<ParcelResponse> findAll() {
    return service.findAll().stream().map(ParcelResponse::from).toList();
  }

  @GetMapping("/{id}")
  public ParcelResponse findById(@PathVariable long id) {
    return ParcelResponse.from(service.findById(id));
  }

  // Beispiel für C1. Dokumentiert die GET-Methoden nach diesem Muster.
  @Operation(summary = "Parcel anlegen",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          required = true,
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = CreateParcelRequest.class))))
  @ApiResponse(responseCode = "201", description = "Ressource angelegt",
      headers = @Header(name = "Location", description = "URL der neuen Ressource",
          schema = @Schema(type = "string", format = "uri")),
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ParcelResponse.class)))
  @ApiResponse(responseCode = "400", description = "Eingabe verletzt die Validierung",
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(responseCode = "409", description = "Eindeutiger Wert bereits vergeben",
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ApiError.class)))
  @PostMapping
  public ResponseEntity<ParcelResponse> create(@Valid @RequestBody CreateParcelRequest request) {
    var response = ParcelResponse.from(service.create(request));
    var location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(response.id()).toUri();
    return ResponseEntity.created(location).body(response);
  }
}
