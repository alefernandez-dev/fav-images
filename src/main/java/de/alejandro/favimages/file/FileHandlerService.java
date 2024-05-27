package de.alejandro.favimages.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileHandlerService {

    private final static String DIRECTORY = System.getenv("FAV_IMAGES_DIRECTORY");
    private final static Logger log = LoggerFactory.getLogger(FileHandlerService.class);

    public void write(InputStream inputStream, String fileName) {
        try {
            var destination = Path.of(DIRECTORY, fileName);
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new FileWriteException();
        }
    }

    public byte[] read(String fileName) {
        try {
            var imagePath = Paths.get(DIRECTORY, fileName);
            if (!Files.exists(imagePath)) {
                throw new FileNotFoundException();
            }
            return Files.readAllBytes(imagePath);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new FileReadException();
        }
    }

    public void remove(String fileName) {
        try {
            var imagePath = Paths.get(DIRECTORY, fileName);
            if (Files.exists(imagePath)) {
                Files.delete(imagePath);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new FileDeleteException();
        }
    }
}
