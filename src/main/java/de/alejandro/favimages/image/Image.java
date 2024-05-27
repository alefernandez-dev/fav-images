package de.alejandro.favimages.image;



import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "images")
public record Image(@Id UUID id, String name, String detail, String imageFileName, ImageCategory category) {

    public Image {
        ImageExtensionValidator.validate(imageFileName);
    }

    public static Image create(String name, String detail, String imageFileName, ImageCategory category) {
        return new Image(UUID.randomUUID(), name, detail, generateFileName(imageFileName), category);
    }

    public Image update(String name, String detail) {
        return new Image(id, name, detail, imageFileName, category);
    }

    public Image changeImageFileName(String imageName) {
        return new Image(id, name, detail, generateFileName(imageName), category);
    }

    public Image changeCategory(Long categoryId, String categoryName) {
        return new Image(id, name, detail, imageFileName, new ImageCategory(categoryName, categoryId));
    }

    private static String generateFileName(String imageFileName) {
        int lastIndexOfDot = imageFileName.lastIndexOf(".");
        return String.join("fav-images", UUID.randomUUID().toString(), imageFileName.substring(lastIndexOfDot));
    }
}
