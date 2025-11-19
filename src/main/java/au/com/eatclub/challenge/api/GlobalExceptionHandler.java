package au.com.eatclub.challenge.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * A global exception handler for the application that provides centralized
 * exception handling across all controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles `IllegalArgumentException` exceptions thrown by any controller.
     *
     * @param ex the `IllegalArgumentException` that was thrown
     * @return a `ResponseEntity` containing an error response with a
     *         `BAD_REQUEST` HTTP status and a descriptive error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorResponse("invalid_time_format", ex.getMessage())
                );
    }

    /**
     * A record representing the structure of an error response.
     *
     * @param error   a short code identifying the type of error
     * @param message a detailed message describing the error
     */
    public record ErrorResponse(String error, String message) {}
}