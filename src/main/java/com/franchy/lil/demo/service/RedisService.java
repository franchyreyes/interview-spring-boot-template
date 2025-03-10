package com.franchy.lil.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisService<T> {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void save(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void save(String key, List<T> value) {
        if (value != null && !value.isEmpty()) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            System.out.println("La lista está vacía o es nula, no se guardará en Redis.");
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> getEntities(String key) {
        Object obj = redisTemplate.opsForValue().get(key);
        if (obj instanceof List<?>) {
            return (List<T>) obj;
        } else {
            throw new ClassCastException("The object retrieved from Redis is not a List");
        }
    }

    public <T> T get(String key, Class<T> clazz) {
        Object obj = redisTemplate.opsForValue().get(key);

        if (clazz.isInstance(obj)) {
            return clazz.cast(obj);
        } else {
            throw new ClassCastException("The object retrieved from Redis is not of type " + clazz.getName());
        }
    }

    public boolean checkKeyExists(String key) {
        return this.redisTemplate.hasKey(key);
    }
}
