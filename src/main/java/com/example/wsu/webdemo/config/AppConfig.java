package com.example.wsu.webdemo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure application with additional beans
 */
@Configuration
public class AppConfig {

    // Add a ModelMapper instance we can use to map entity instances
    // into DTO instances and vice versa
    @Bean
    public ModelMapper registerMapper() {
        return new ModelMapper();
    }
}
