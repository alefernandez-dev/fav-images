package de.alejandro.favimages.image;

import de.alejandro.favimages.file.*;
import de.alejandro.favimages.shared.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ImageExceptionHandler {


    private static final Logger log = LoggerFactory.getLogger(ImageExceptionHandler.class);

    @ExceptionHandler({CategoryForImageNotFoundException.class, ImageNotFoundException.class, InvalidExtensionException.class})
    public ResponseEntity<MessageResponse<String>> handleImageExceptions(RuntimeException e) {
        return switch (e) {
            case CategoryForImageNotFoundException ignored -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse<>("category for image not found", false, 404));
            case ImageNotFoundException ignored -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse<>("image not found", false, 404));
            case InvalidExtensionException ignored -> ResponseEntity.badRequest().body(new MessageResponse<>("invalid extension file, allowed extensions: jpg|png|gif|bmp", false, 400));
            default -> throw e;
        };
    }


    @ExceptionHandler({FileDeleteException.class, FileNotFoundException.class, FileReadException.class, FileWriteException.class})
    public ResponseEntity<MessageResponse<String>> handleFileHandlerExceptions(RuntimeException e) {
        return switch (e) {
            case FileNotFoundException ignored -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse<>("image file not found", false, 404));
            case FileDeleteException ignored -> ResponseEntity.internalServerError().body(new MessageResponse<>("error to delete file", false, 500));
            case FileReadException ignored -> ResponseEntity.internalServerError().body(new MessageResponse<>("error to read file", false, 500));
            case FileWriteException ignored -> ResponseEntity.internalServerError().body(new MessageResponse<>("error to write file", false, 500));
            default -> throw e;
        };
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<MessageResponse<String>> handleIOException(IOException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError().body(new MessageResponse<>("input/output error", false, 500));
    }

}
