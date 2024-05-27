package de.alejandro.favimages.shared;

import java.io.Serializable;

public record MessageResponse<T> (T response, boolean success, int code) implements Serializable {
}
