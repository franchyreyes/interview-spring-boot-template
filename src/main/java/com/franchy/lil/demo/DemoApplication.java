package com.franchy.lil.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories(basePackages = "com.franchy.lil.demo.repository.redis")
@EnableJpaRepositories(basePackages = "com.franchy.lil.demo.repository.jpa")
public class DemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        logger.debug("Starting DemoApplication");
        SpringApplication.run(DemoApplication.class, args);
        logger.debug("DemoApplication started successfully");
    }
}