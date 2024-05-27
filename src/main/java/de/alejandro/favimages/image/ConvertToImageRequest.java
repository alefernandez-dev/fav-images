package de.alejandro.favimages.image;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class ConvertToImageRequest {
    public static ImageRequest convert(String stringImageRequest) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(stringImageRequest, ImageRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
