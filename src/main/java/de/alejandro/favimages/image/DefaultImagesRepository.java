package de.alejandro.favimages.image;

import de.alejandro.favimages.category.DefaultCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DefaultImagesRepository implements ImageRepository {

    private static final Logger log = LoggerFactory.getLogger(DefaultImagesRepository.class);


    private static final String LIST_IMAGES = """
             SELECT i.id AS image_id,
                  i.name AS image_name,
                  i.detail AS image_detail,
                  i.file_name AS image_file_name,
                  c.id AS category_id,
                  c.name AS category_name
             FROM images i
             JOIN categories c ON i.category_id = c.id
             LIMIT :limit;
             """;
    private static final String LIST_BY_CATEGORY = """
            SELECT
                i.id AS image_id,
                i.name AS image_name,
                i.detail AS image_detail,
                i.file_name AS image_file_name,
                c.id AS category_id,
                c.name AS category_name
            FROM images i
            JOIN categories c ON i.category_id = c.id
            WHERE c.id = :categoryId
            LIMIT :limit;
            """;
    private static final String FIND_IMAGE_BY_ID = """
            SELECT
                i.id AS image_id,
                i.name AS image_name,
                i.detail AS image_detail,
                i.file_name AS image_file_name,
                c.id AS category_id,
                c.name AS category_name
            FROM images i
            JOIN categories c ON i.category_id = c.id
            WHERE i.id = :imageId;
            """;
    private static final String PERSIST_IMAGE = "INSERT INTO images (id, name, detail, file_name, category_id) VALUES (:id, :name, :detail, :fileName, :categoryId)";
    private static final String DELETE_IMAGE = "DELETE FROM images WHERE id = :imageId;";
    private static final String UPDATE_IMAGE = "UPDATE images SET name = :name, detail = :detail, file_name = :fileName, category_id = :categoryId WHERE id = :id;";

    private final JdbcClient jdbcClient;

    public DefaultImagesRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Image> list(int limit) {
        return jdbcClient.sql(LIST_IMAGES).param("limit", limit).query(new ImageRowMapper()).list();
    }

    @Override
    public List<Image> findByNameAndDetail(String nameOrDetail, int limit) {
        return List.of();
    }

    @Override
    public List<Image> findByCategory(Long catgoryId, int limit) {
        return jdbcClient.sql(LIST_BY_CATEGORY)
                .param("categoryId", catgoryId)
                .param("limit", limit)
                .query(new ImageRowMapper())
                .list();
    }

    @Override
    public Optional<Image> findById(UUID imageId) {
        try {
            var image = jdbcClient
                    .sql(FIND_IMAGE_BY_ID)
                    .param("imageId", imageId)
                    .query(new ImageRowMapper())
                    .single();
            return Optional.of(image);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public void persist(Image image) {
        jdbcClient
                .sql(PERSIST_IMAGE)
                .param("id", image.id())
                .param("name", image.name())
                .param("detail", image.detail())
                .param("fileName", image.imageFileName())
                .param("categoryId", image.category().categoryId())
                .update();
    }

    @Override
    public void delete(UUID imageId) {
        jdbcClient.sql(DELETE_IMAGE).param("imageId", imageId).update();
    }

    @Override
    public void update(Image image, UUID imageId) {
        jdbcClient
                .sql(UPDATE_IMAGE)
                .param("name", image.name())
                .param("detail", image.detail())
                .param("fileName", image.imageFileName())
                .param("categoryId", image.category().categoryId())
                .param("id", imageId)
                .update();
    }
}
