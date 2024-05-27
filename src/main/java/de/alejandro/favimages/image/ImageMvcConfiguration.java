package de.alejandro.favimages.image;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class ImageMvcConfiguration implements WebMvcConfigurer {

    private final static String DIRECTORY = System.getenv("FAV_IMAGES_DIRECTORY");


    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        var location = "file:" + DIRECTORY;
        registry.addResourceHandler("/images/**").addResourceLocations(location);
    }

}
