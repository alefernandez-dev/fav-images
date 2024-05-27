package de.alejandro.favimages.image;

import de.alejandro.favimages.shared.MessageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    @Value("${application.base-url-with-port}")
    private String baseUrl;

    private final ImageService service;

    public ImageController(ImageService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestPart String image, @RequestPart("file") MultipartFile file) throws IOException {
        var request = ConvertToImageRequest.convert(image);
        service.create(
                request.name(),
                request.detail(),
                request.category(),
                file.getInputStream(),
                file.getOriginalFilename()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ImageResponse>> list(@RequestParam(required = false, defaultValue = "10") int limit) {
        var images = service.list(limit)
                .stream()
                .map(i -> new ImageResponse(
                        i.id(),
                        i.name(),
                        i.detail(),
                        i.category().categoryId(),
                        i.category().categoryName(),
                        String.join("/", baseUrl, "images",i.imageFileName())
                ))
                .toList();
        return ResponseEntity.ok(images);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ImageResponse>> findByCategory(@RequestParam(required = false, defaultValue = "10") int limit, @PathVariable Long categoryId) {
        var images = service.findByCategory(categoryId, limit)
                .stream()
                .map(i -> new ImageResponse(
                        i.id(),
                        i.name(),
                        i.detail(),
                        i.category().categoryId(),
                        i.category().categoryName(),
                        String.join("/", baseUrl, "images", i.imageFileName())
                ))
                .toList();
        return ResponseEntity.ok(images);
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<?> delete(@PathVariable String imageId) {
        service.delete(UUID.fromString(imageId));
        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "/download/{imageName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> finImageFileByName(@PathVariable String imageName) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(service.findImageFileByName(imageName));
    }

    @PatchMapping("/{imageId}")
    public ResponseEntity<MessageResponse<String>> update(@RequestBody ImageRequest request, @PathVariable String imageId) {
        service.update(request.name(), request.detail(), imageId);
        return ResponseEntity.ok(new MessageResponse<>("image updated successfully", true, 200));
    }

    @PatchMapping("/{imageId}/category/{categoryId}")
    public ResponseEntity<?> changeCategory(@PathVariable Long categoryId,@PathVariable UUID imageId) {
        service.changeCategory(categoryId, imageId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{imageId}/file")
    public ResponseEntity<?> changeImageFile(@RequestParam("file") MultipartFile file, @PathVariable UUID imageId) throws IOException {
        service.changeImageFile(
                file.getInputStream(),
                file.getOriginalFilename(),
                imageId
        );
        return ResponseEntity.ok().build();
    }

}
