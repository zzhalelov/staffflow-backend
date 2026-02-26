package kz.zzhalelov.staffflow.server.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {
    @Schema(example = "2026-02-25T22:50:15")
    private LocalDateTime timestamp;

    @Schema(example = "404")
    private int status;

    @Schema(example = "Not Found")
    private String error;

    @Schema(example = "Ресурс не найден")
    private String message;

    public static ErrorResponse of(HttpStatus status, String message) {
        return new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message
        );
    }
}