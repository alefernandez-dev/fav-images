package de.alejandro.favimages.category;

import de.alejandro.favimages.shared.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CategoryExceptionHandler {

    @ExceptionHandler({CategoryNotFoundException.class, CategoryNameAlreadyExistsException.class})
    public ResponseEntity<MessageResponse<String>> handleCategoryExceptions(RuntimeException e) {
        return switch (e) {
            case CategoryNameAlreadyExistsException ignored -> ResponseEntity.badRequest().body(new MessageResponse<>("category name already exist", false, 400));
            case CategoryNotFoundException ignored -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse<>("category not found", false, 404));
            default -> throw e;
        };
    }
}
