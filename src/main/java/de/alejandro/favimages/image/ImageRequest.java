package de.alejandro.favimages.image;

import java.io.Serializable;

public record ImageRequest(String name, String detail, Long category) implements Serializable {
}
