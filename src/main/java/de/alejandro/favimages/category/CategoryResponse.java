package de.alejandro.favimages.category;

import java.io.Serializable;

public record CategoryResponse(Long id, String name) implements Serializable {
}
