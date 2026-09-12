package course.parceldelivery.api;

import course.parceldelivery.service.ParcelService;
import course.parceldelivery.web.ApiError;
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
@RequestMapping("/api/parcels")
public class ParcelController {
  private final ParcelService service;

  public ParcelController(ParcelService service) {
    this.service = service;
  }

  @Operation(summary = "Alle Pakete lesen", operationId = "listParcels")
  @ApiResponse(
      responseCode = "200",
      description = "Liste aller Pakete",
      content =
          @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ParcelResponse.class))))
  @GetMapping
  public List<ParcelResponse> findAll() {
    return service.findAll().stream().map(ParcelResponse::from).toList();
  }

  @Operation(summary = "Paket lesen", operationId = "getParcel")
  @ApiResponse(
      responseCode = "200",
      description = "Paket gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ParcelResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Paket nicht gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @GetMapping("/{id}")
  public ParcelResponse findById(@PathVariable long id) {
    return ParcelResponse.from(service.findById(id));
  }

  @Operation(
      summary = "Paket anlegen",
      operationId = "createParcel",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = CreateParcelRequest.class))))
  @ApiResponse(
      responseCode = "201",
      description = "Paket angelegt",
      headers =
          @Header(
              name = "Location",
              description = "URL der angelegten Ressource",
              schema = @Schema(type = "string", format = "uri")),
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ParcelResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Eingabe verletzt die Validierung",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "409",
      description = "trackingCode darf nicht doppelt vorkommen",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @PostMapping
  public ResponseEntity<ParcelResponse> create(@Valid @RequestBody CreateParcelRequest request) {
    var response = ParcelResponse.from(service.create(request));
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }
}
