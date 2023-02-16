package com.example.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisManager {

    private final RedisTemplate redisTemplate;

    public <T> boolean saveData(String key, T data, long ttl) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String value = mapper.writeValueAsString(data);
            redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(ttl));
            return true;
        } catch(Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

    public <T> T getData(String key, Class<T> classType) {
        String value = (String)redisTemplate.opsForValue().get(key);

        if(value == null){
            return null;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(value,classType);
        } catch(Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}
