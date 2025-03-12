package com.franchy.lil.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class RedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        logger.debug("Creating JedisConnectionFactory bean");
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        logger.debug("JedisConnectionFactory bean created successfully");
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        logger.debug("Creating RedisTemplate bean");
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        logger.debug("RedisTemplate bean created successfully");
        return template;
    }
}