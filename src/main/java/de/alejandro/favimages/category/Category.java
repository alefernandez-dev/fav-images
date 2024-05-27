package de.alejandro.favimages.category;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "categories")
public record Category(@Id Long id, String name) {
    public static Category create(String name) {
        return new Category(null, name);
    }

    public Category update(String name) {
        return new Category(id, name);
    }
}
