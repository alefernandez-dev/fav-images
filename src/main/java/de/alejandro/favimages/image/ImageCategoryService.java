package de.alejandro.favimages.image;

import de.alejandro.favimages.category.CategoryRepository;

public class ImageCategoryService {

    private final CategoryRepository categoryRepository;

    public ImageCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public String getCategoryNameById(Long categoryId) {
        return categoryRepository
                .findCategoryNameById(categoryId)
                .orElseThrow(CategoryForImageNotFoundException::new);
    }

}
