package course.eventtickets.api;

import course.eventtickets.service.VenueCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Eingabe für Spielorte")
public record CreateVenueRequest(
    @Schema(description = "Eindeutiger Spielortcode", example = "V-99")
        @NotBlank
        @Size(min = 2, max = 12)
        String venueCode,
    @Schema(description = "Name des Spielorts", example = "Testhalle")
        @NotBlank
        @Size(min = 1, max = 120)
        String name,
    @Schema(description = "Straße und Hausnummer", example = "Testweg 1")
        @NotBlank
        @Size(min = 1, max = 120)
        String street,
    @Schema(description = "Postleitzahl", example = "45127") @NotBlank @Pattern(regexp = "\\d{5}")
        String postalCode,
    @Schema(description = "Ort", example = "Essen") @NotBlank @Size(min = 1, max = 80) String city,
    @Schema(description = "Maximale Besucherzahl", example = "300") @NotNull @Min(1) @Max(50000)
        Integer capacity) {
  public VenueCommand toCommand() {
    return new VenueCommand(venueCode, name, street, postalCode, city, capacity);
  }
}
