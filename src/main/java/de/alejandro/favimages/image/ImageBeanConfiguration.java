package de.alejandro.favimages.image;

import de.alejandro.favimages.category.CategoryRepository;
import de.alejandro.favimages.file.FileHandlerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;

@Configuration
public class ImageBeanConfiguration {

    @Bean
    FileHandlerService imageFileManager() {
        return new FileHandlerService();
    }

    @Bean
    ImageRepository imageRepository(JdbcClient jdbcClient) {
        return new DefaultImagesRepository(jdbcClient);
    }

    @Bean
    ImageCategoryService imageCategoryService(CategoryRepository categoryRepository) {
        return new ImageCategoryService(categoryRepository);
    }

    @Bean
    ImageService imageService(ImageRepository imageRepository, ImageCategoryService imageCategoryService, FileHandlerService imageFileManager) {
        return new ImageService(imageRepository, imageCategoryService, imageFileManager);
    }

}
