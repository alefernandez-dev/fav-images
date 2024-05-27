package de.alejandro.favimages.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, NoHandlerFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<MessageResponse<String>> handleSpringException(Exception e) throws Exception {
        log.error(e.getMessage(), e);
        return switch (e) {
            case HttpRequestMethodNotSupportedException ignored -> ResponseEntity.badRequest().body(new MessageResponse<>("request method is not supported", false, 400));
            case NoHandlerFoundException ignored -> ResponseEntity.badRequest().body(new MessageResponse<>("resource not found. Please check your request URL", false, 400));
            case NoResourceFoundException ignored -> ResponseEntity.internalServerError().body(new MessageResponse<>("resource not found", false, 500));
            default -> throw e;
        };
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse<String>> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError().body(new MessageResponse<>("internal server error", false, 500));
    }

}
