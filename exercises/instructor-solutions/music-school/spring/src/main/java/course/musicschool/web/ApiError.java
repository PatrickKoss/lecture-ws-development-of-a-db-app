package course.musicschool.web;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

@Schema(description = "Stabiler Fehlerkörper für alle API-Fehler")
public record ApiError(
    @Schema(example = "VALIDATION_FAILED", requiredMode = Schema.RequiredMode.REQUIRED) String code,
    @Schema(example = "Eingabe ist ungültig", requiredMode = Schema.RequiredMode.REQUIRED)
        String message,
    @Schema(example = "request-42", requiredMode = Schema.RequiredMode.REQUIRED)
        String correlationId,
    @Schema(
            description = "Fehler je Eingabefeld, sonst leer",
            requiredMode = Schema.RequiredMode.REQUIRED)
        Map<String, String> fields) {}
