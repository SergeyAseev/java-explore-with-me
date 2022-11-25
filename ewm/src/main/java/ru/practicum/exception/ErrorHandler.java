package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler extends RuntimeException {

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleValidateException(
            final ValidationException e) {
        log.info(e.getMessage());
        //String errors = Collections.singletonList(e.printStackTrace());

        StringWriter errors = new StringWriter();
        PrintWriter pw = new PrintWriter(errors);
        e.printStackTrace(pw);
        return new ResponseEntity<>(Map.of("error",
                errors.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleNotFoundException(
            final NotFoundException e) {
        log.info(e.getMessage(), e);
        return new ResponseEntity<>(Map.of("error",
                e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleExistsElementException(
            final ExistsElementException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(Map.of("error", e.getMessage()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(
            final IllegalArgumentException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(Map.of("error",
                e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

/*    @ExceptionHandler
    public ResponseEntity<?> validationException(ValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> notFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }*/
}
