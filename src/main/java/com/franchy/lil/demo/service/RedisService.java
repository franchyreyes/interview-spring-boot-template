package com.franchy.lil.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class RedisService<T> {

    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void save(String key, T value) {
        logger.debug("Saving value with key: {}", key);
        this.redisTemplate.opsForValue().set(key, value);
        logger.debug("Value saved successfully");
    }

    public void save(String key, List<T> value) {
        if (value != null && !value.isEmpty()) {
            logger.debug("Saving list with key: {}", key);
            this.redisTemplate.opsForValue().set(key, value);
            logger.debug("List saved successfully");
        } else {
            logger.warn("The list is empty or null, it will not be saved in Redis.");
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> getEntities(String key) {
        logger.debug("Retrieving entities with key: {}", key);
        Object obj = this.redisTemplate.opsForValue().get(key);
        if (obj instanceof List<?>) {
            logger.debug("Entities retrieved successfully");
            return (List<T>) obj;
        } else {
            logger.error("The object retrieved from Redis is not a List");
            throw new ClassCastException("The object retrieved from Redis is not a List");
        }
    }

    public <T> T get(String key, Class<T> clazz) {
        logger.debug("Retrieving value with key: {}", key);
        Object obj = this.redisTemplate.opsForValue().get(key);

        if (clazz.isInstance(obj)) {
            logger.debug("Value retrieved successfully");
            return clazz.cast(obj);
        } else {
            logger.error("The object retrieved from Redis is not of type {}", clazz.getName());
            throw new ClassCastException("The object retrieved from Redis is not of type " + clazz.getName());
        }
    }

    public boolean checkKeyExists(String key) {
        logger.debug("Checking if key exists: {}", key);
        boolean exists = this.redisTemplate.hasKey(key);
        logger.debug("Key exists: {}", exists);
        return exists;
    }
}