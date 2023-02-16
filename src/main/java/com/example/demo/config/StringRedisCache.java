package com.example.demo.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class StringRedisCache {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static <K> String createRedisKey(String cacheName, K key) {
        return String.format("STRING_CACHE.%s:%s", cacheName, key);
    }


    public <K> boolean hasKey(final String cacheName, K key) {
        final String redisKey = createRedisKey(cacheName, key);
        return stringRedisTemplate.hasKey(redisKey);
    }

    public <K, V> List<V> multiGet(final String cacheName, final List<K> keys, final TypeReference<V> typeReference) throws Exception {

        final List<String> redisKeys = keys.stream().map(key -> createRedisKey(cacheName, key)).collect(Collectors.toList());

        List<V> result = new ArrayList();
        try {
            List<String> redisDatas = stringRedisTemplate.opsForValue().multiGet(redisKeys);

            for (String redisData : CollectionUtils.emptyIfNull(redisDatas)) {
                V v = null;

                if (StringUtils.isNotBlank(redisData)) {
                    addData(result, redisData, typeReference);
                }

                result.add(v);
            }

        } catch (Exception e) {
            log.error("Redis get Fail fail(key={})", redisKeys, e);
            throw new Exception("Redis Get Fail", e);
        }

        return result;
    }

    private <V> void addData(List<V> result, String redisData, TypeReference<V> typeReference) {
        try {
            result.add(objectMapper.readValue(redisData, typeReference));
        } catch (IOException e) {
            log.error("redisData : {}", redisData, e);
        }
    }

    public <K, V> V get(final String cacheName, final K key, final TypeReference<V> typeReference) throws Exception {

        final String redisKey = createRedisKey(cacheName, key);
        String redisData = null;

        try {
            redisData = stringRedisTemplate.opsForValue().get(redisKey);

            if (StringUtils.isBlank(redisData)) {
                return null;
            }

            return objectMapper.readValue(redisData, typeReference);
        } catch (IOException e) {
            log.error("redisData : {}", redisData, e);
            throw new Exception("Json String Read Fail", e);
        } catch (Exception e) {
            log.error("Redis get Fail fail(key={})", redisKey, e);
            throw new Exception("Redis Get Fail", e);
        }

    }


    public <K, V> void set(final String cacheName, final K key, final V value, final long timeout, final TimeUnit unit) throws Exception {

        final String redisKey = createRedisKey(cacheName, key);

        try {
            String redisData = objectMapper.writeValueAsString(value);
            stringRedisTemplate.opsForValue().set(redisKey, redisData, timeout, unit);

        } catch (IOException e) {
            throw new Exception("Json String Write Fail", e);
        } catch (Exception e) {
            log.error("Redis set fail (key={})", redisKey, e);
            throw new Exception("Redis Set Fail", e);
        }
    }

    public <K, V> Boolean setIfAbsent(final String cacheName, final K key, final V value, final long timeout, final TimeUnit unit) throws Exception {

        final String redisKey = createRedisKey(cacheName, key);

        try {
            String redisData = objectMapper.writeValueAsString(value);
            return stringRedisTemplate.opsForValue().setIfAbsent(redisKey, redisData, timeout, unit);

        } catch (IOException e) {
            throw new Exception("Json String Write Fail", e);
        } catch (Exception e) {
            log.error("Redis set fail (key={})", redisKey, e);
            throw new Exception("Redis Set Fail", e);
        }
    }

    public <K, V> void set(final String cacheName, final K key, final V value) throws Exception {
        final String redisKey = createRedisKey(cacheName, key);

        try {
            String redisData = objectMapper.writeValueAsString(value);

            stringRedisTemplate.opsForValue().set(redisKey, redisData);

        } catch (IOException e) {
            throw new Exception("Json String Write Fail", e);
        } catch (Exception e) {
            log.error("Redis set fail (key={})", redisKey, e);
            throw new Exception("Redis Set Fail", e);
        }
    }

    public <K, V> Boolean setIfAbsent(final String cacheName, final K key, final V value) throws Exception {
        final String redisKey = createRedisKey(cacheName, key);

        try {
            String redisData = objectMapper.writeValueAsString(value);

            return stringRedisTemplate.opsForValue().setIfAbsent(redisKey, redisData);

        } catch (IOException e) {
            throw new Exception("Json String Write Fail", e);
        } catch (Exception e) {
            log.error("Redis set fail (key={})", redisKey, e);
            throw new Exception("Redis Set Fail", e);
        }
    }

    public <K> void delete(final String cacheName, final K key) throws Exception {
        final String redisKey = createRedisKey(cacheName, key);

        try {
            stringRedisTemplate.delete(redisKey);
        } catch (Exception e) {
            log.error("Redis delete fail (key={})", redisKey, e);
            throw new Exception("Redis delete Fail", e);
        }
    }

    public <K, V> V load(final String cacheName, final Long timeout, final TimeUnit unit, final K key, final RedisLoader<K, V> loader)
            throws Exception {

        if (hasKey(cacheName, key)) {
            return get(cacheName, key, new TypeReference<V>() {
            });
        }
        if (loader != null) {
            V value = null;
            try {
                value = loader.load(key);
            } catch (Exception e) {
                log.error("Data Load Fail, cacheName={}, key={}", cacheName, key, e);
            }
            try {
                if (value != null) {
                    if (timeout == null || unit == null) {
                        set(cacheName, key, value);
                    } else {
                        set(cacheName, key, value, timeout, unit);
                    }
                    return value;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;


    }

}
