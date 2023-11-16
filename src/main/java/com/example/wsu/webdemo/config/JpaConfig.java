package com.example.wsu.webdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configure application to detect and use JPA repositories
 *
 * Note: must be done here as opposed to annotating the application
 * class so we're able to fire up a partial application context for MockMvc tests
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.example.wsu.webdemo")
public class JpaConfig {
}
