package com.nomadnetwork.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Get absolute path
        File uploadDir = new File("uploads/places");
        String absolutePath = uploadDir.getAbsolutePath();
        
        registry.addResourceHandler("/**")
        .addResourceLocations("classpath:/static/");
        
        
        
        registry.addResourceHandler("/uploads/places/**")
        .addResourceLocations("file:" + absolutePath + "/");
    }
}