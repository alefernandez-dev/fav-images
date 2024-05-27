package de.alejandro.favimages.category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> list(int limit);
    void persist(Category category);
    void update(Category category);
    Optional<Category> findCategoryById(Long categoryId);
    boolean existsByName(String categoryName);
    boolean existsByNameAndIdNot(String categoryName, Long id);



    // for image package
    Optional<String> findCategoryNameById(Long categoryId);
}
