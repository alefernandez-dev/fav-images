package de.alejandro.favimages.category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;
import java.util.Optional;

public class DefaultCategoryRepository implements CategoryRepository {

    private static final Logger log = LoggerFactory.getLogger(DefaultCategoryRepository.class);

    private static final String LIST_CATEGORIES = "SELECT * FROM categories LIMIT :limit;";
    private static final String PERSIST_CATEGORY = "INSERT INTO categories (name) VALUES (:name);";
    private static final String UPDATE_CATEGORY = "UPDATE categories SET name = :name WHERE id = :id";
    private static final String FIND_CATEGORY_BY_ID = "SELECT * FROM categories WHERE id = :id";
    private static final String EXISTS_BY_NAME = "SELECT COUNT(*) FROM categories WHERE name = :name";
    private static final String EXISTS_BY_NAME_AND_NOT_ID = "SELECT COUNT(*) FROM categories WHERE name = :name and id != :id";

    private static final String FIND_CATEGORY_NAME__BY_ID = "SELECT name FROM categories WHERE id = :id";

    private final JdbcClient jdbcClient;

    public DefaultCategoryRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Category> list(int limit) {
        return jdbcClient
                .sql(LIST_CATEGORIES)
                .param("limit", limit)
                .query(Category.class)
                .list();
    }

    @Override
    public void persist(Category category) {
        jdbcClient
                .sql(PERSIST_CATEGORY)
                .param("name", category.name())
                .update();
    }

    @Override
    public void update(Category category) {
        jdbcClient
                .sql(UPDATE_CATEGORY)
                .param("name", category.name())
                .param("id", category.id())
                .update();
    }

    @Override
    public Optional<Category> findCategoryById(Long categoryId) {
        try {
            var category = jdbcClient
                    .sql(FIND_CATEGORY_BY_ID)
                    .param("id", categoryId)
                    .query(Category.class)
                    .single();
            return Optional.of(category);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByName(String categoryName) {
        var count = jdbcClient
                .sql(EXISTS_BY_NAME)
                .param("name", categoryName)
                .query(Integer.class)
                .single();
        return count > 0;
    }

    @Override
    public boolean existsByNameAndIdNot(String categoryName, Long id) {
        var count = jdbcClient
                .sql(EXISTS_BY_NAME_AND_NOT_ID)
                .param("name", categoryName)
                .param("id", id)
                .query(Integer.class)
                .single();
        return count > 0;
    }

    @Override
    public Optional<String> findCategoryNameById(Long categoryId) {
        try {
            var categoryName = jdbcClient
                    .sql(FIND_CATEGORY_NAME__BY_ID)
                    .param("id", categoryId)
                    .query(String.class)
                    .single();
            return Optional.of(categoryName);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
