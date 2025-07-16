package com.franchy.lil.demo.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.franchy.lil.demo.repository.jpa")
@ConditionalOnProperty(name = "security.web.enabled", havingValue = "true", matchIfMissing = true)
public class JPAConfig {
}
