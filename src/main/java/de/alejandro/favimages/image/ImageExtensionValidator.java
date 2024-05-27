package de.alejandro.favimages.image;

public class ImageExtensionValidator {
    public static void validate(String imageFileName) {
        String extensionPattern = "^.+\\.(?i)(jpg|png|gif|bmp)$";
        if (imageFileName == null || !imageFileName.matches(extensionPattern)) {
            throw new InvalidExtensionException();
        }
    }
}
