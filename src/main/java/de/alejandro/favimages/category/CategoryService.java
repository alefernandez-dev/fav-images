package de.alejandro.favimages.category;

import java.util.List;

public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public void create(String name) {
        if(repository.existsByName(name)) {
            throw new CategoryNameAlreadyExistsException();
        }
        repository.persist(Category.create(name));
    }

    public void update(String name, Long id) {

        var category = this.getCategory(id);
        var categoryUpdated = category.update(name);

        if(repository.existsByNameAndIdNot(categoryUpdated.name(), categoryUpdated.id())) {
            throw new CategoryNameAlreadyExistsException();
        }

        repository.update(categoryUpdated);
    }

    public List<Category> list(int limit) {
        return repository.list(limit);
    }

    public Category getCategoryById(Long id) {
        return this.getCategory(id);
    }

    private Category getCategory(Long id) {
        return repository
                .findCategoryById(id)
                .orElseThrow(CategoryNotFoundException::new);
    }

}
