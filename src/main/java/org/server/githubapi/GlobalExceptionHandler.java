package org.server.githubapi;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        ErrorResponse error = new ErrorResponse(ex.getStatusCode().value(), ex.getReason());
        return new ResponseEntity<>(error, ex.getStatusCode());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> handleHttpClientErrorException(HttpClientErrorException ex) {
        ErrorResponse error = new ErrorResponse(ex.getStatusCode().value(),
                "GitHub API client error: " + ex.getStatusText() + " - " + ex.getResponseBodyAsString());
        return new ResponseEntity<>(error, ex.getStatusCode());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleHttpServerErrorException(HttpServerErrorException ex) {
        ErrorResponse error = new ErrorResponse(ex.getStatusCode().value(),
                "GitHub API server error: " + ex.getStatusText());
        return new ResponseEntity<>(error, ex.getStatusCode());
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ErrorResponse> handleRestClientException(RestClientException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(),
                "Error communicating with GitHub API: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Getter
    static class ErrorResponse {
        private final int status;
        private final String message;
        private Map<String, String> errors;

        public ErrorResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public ErrorResponse(int status, String message, Map<String, String> errors) {
            this.status = status;
            this.message = message;
            this.errors = errors;
        }
    }
}
