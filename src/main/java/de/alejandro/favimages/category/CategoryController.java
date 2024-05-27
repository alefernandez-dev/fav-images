package de.alejandro.favimages.category;

import de.alejandro.favimages.shared.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<MessageResponse<List<CategoryResponse>>> list(@RequestParam(required = false, defaultValue = "10") int limit) {

        var categories = service
                .list(limit)
                .stream()
                .map(c -> new CategoryResponse(c.id(), c.name()))
                .toList();

        return ResponseEntity.ok(new MessageResponse<>(categories, true, 200));

    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse<CategoryResponse>> getById(@PathVariable Long id) {
        var category = service.getCategoryById(id);
        return ResponseEntity.ok(new MessageResponse<>(new CategoryResponse(category.id(), category.name()), true, 200));
    }

    @PostMapping
    public ResponseEntity<MessageResponse<String>> create(@RequestBody CategoryRequest request) {
        service.create(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse<>("category created successfully", true, 201));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse<String>> update(@RequestBody CategoryRequest request,@PathVariable Long id) {
        service.update(request.name(), id);
        return ResponseEntity.ok(new MessageResponse<>("category updated successfully", true, 200));
    }


}
