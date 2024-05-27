package de.alejandro.favimages.image;

import de.alejandro.favimages.file.FileHandlerService;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageCategoryService imageCategoryService;
    private final FileHandlerService fileHandlerService;

    public ImageService(ImageRepository imageRepository, ImageCategoryService imageCategoryService, FileHandlerService fileHandlerService) {
        this.imageRepository = imageRepository;
        this.imageCategoryService = imageCategoryService;
        this.fileHandlerService = fileHandlerService;
    }

    public void create(String name, String detail, Long categoryId, InputStream inputStream, String imageFileName) {
        var categoryName = imageCategoryService.getCategoryNameById(categoryId);
        var image = Image.create(name, detail, imageFileName, new ImageCategory(categoryName, categoryId));
        imageRepository.persist(image);
        fileHandlerService.write(inputStream, image.imageFileName());
    }

    public byte[] findImageFileByName(String name) {
        return fileHandlerService.read(name);
    }

    public List<Image> list(int limit, String nameOrDetail) {
        return imageRepository.findByNameAndDetail(nameOrDetail, limit);
    }

    public List<Image> findByCategory(Long categoryId, int limit) {
        return imageRepository.findByCategory(categoryId, limit);
    }

    public void delete(UUID imageId) {
        var image = imageRepository
                .findById(imageId)
                .orElseThrow(ImageNotFoundException::new);

        fileHandlerService.remove(image.imageFileName());

        imageRepository.delete(image.id());
    }

    public void update(String name, String detail, String imageId) {
        UUID id = UUID.fromString(imageId);
        var image = imageRepository
                .findById(id)
                .orElseThrow(ImageNotFoundException::new);
        var imageUpdated = image.update(name, detail);
        imageRepository.update(imageUpdated, id);
    }

    public void changeCategory(Long categoryId, UUID imageId) {
        var categoryName = imageCategoryService.getCategoryNameById(categoryId);
        var image = imageRepository
                .findById(imageId)
                .orElseThrow(ImageNotFoundException::new);
        var imageUpdated = image.changeCategory(categoryId, categoryName);
        imageRepository.update(imageUpdated, imageId);
    }

    public void changeImageFile(InputStream inputStream, String imageFileName, UUID imageId) {
        var image = imageRepository
                .findById(imageId)
                .orElseThrow(ImageNotFoundException::new);
        var fileName = image.imageFileName();
        var imageUpdated = image.changeImageFileName(imageFileName);
        fileHandlerService.remove(fileName);
        imageRepository.update(imageUpdated, imageId);
        fileHandlerService.write(inputStream, imageUpdated.imageFileName());
    }
}
