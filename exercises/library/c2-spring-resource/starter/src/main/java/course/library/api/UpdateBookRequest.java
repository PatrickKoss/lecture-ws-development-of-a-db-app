package course.library.api;

import course.library.service.BookCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Eingabe für Bücher")
public record UpdateBookRequest(
    @Schema(description = "ISBN mit 13 Ziffern", example = "9780000000000")
        @NotBlank
        @Pattern(regexp = "\\d{13}")
        String isbn,
    @Schema(description = "Buchtitel", example = "Testbuch") @NotBlank @Size(min = 1, max = 200)
        String title,
    @Schema(description = "Erscheinungsjahr", example = "2026") @NotNull @Min(1450) @Max(2100)
        Integer publicationYear,
    @Schema(description = "Sachgebiet", example = "Informatik") @NotBlank @Size(min = 1, max = 50)
        String subjectArea,
    @Schema(description = "Signatur des Regals", example = "I-99")
        @NotBlank
        @Size(min = 1, max = 10)
        String shelfCode) {
  public BookCommand toCommand() {
    return new BookCommand(isbn, title, publicationYear, subjectArea, shelfCode);
  }
}
