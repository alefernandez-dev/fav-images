package de.alejandro.favimages.image;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageRepository {
    List<Image> list(int limit);
    List<Image> findByNameAndDetail(String nameOrDetail, int limit);
    List<Image> findByCategory(Long catgoryId, int limit);
    Optional<Image> findById(UUID imageId);
    void persist(Image image);
    void delete(UUID imageId);
    void update(Image image, UUID imageId);
}
