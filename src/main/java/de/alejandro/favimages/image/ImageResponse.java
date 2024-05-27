package de.alejandro.favimages.image;

import java.io.Serializable;
import java.util.UUID;

public record ImageResponse(UUID id, String name, String detail, Long category_id, String category_name, String image_url) implements Serializable {
}
