package com.franchy.lil.demo.repository.redis;

import com.franchy.lil.demo.model.OrderRedis;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Service;

@Service
public interface OrderRedisRepository extends KeyValueRepository<OrderRedis, String> {
}
