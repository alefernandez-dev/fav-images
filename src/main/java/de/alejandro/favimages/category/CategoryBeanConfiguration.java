package de.alejandro.favimages.category;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;

@Configuration
public class CategoryBeanConfiguration {

    @Bean
    CategoryRepository categoryRepository(JdbcClient jdbcClient) {
        return new DefaultCategoryRepository(jdbcClient);
    }

    @Bean
    CategoryService categoryService(CategoryRepository categoryRepository) {
        return new CategoryService(categoryRepository);
    }
}
