package com.example.demo.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.Duration;

@EnableCaching
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @SuppressWarnings("deprecation")
    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory());
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())) // Value Serializer 변경
                .prefixKeysWith("Test:") // Key Prefix로 "Test:"를 앞에 붙여 저장
                .entryTtl(Duration.ofMinutes(30)); // 캐시 수명 30분
        builder.cacheDefaults(configuration);
        return builder.build();
    }

//        @Bean
//    public StringRedisTemplate redisTemplate() {
//        StringRedisTemplate redisTemplate = new StringRedisTemplate();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());   // Key: String
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());  // Value: 직렬화에 사용할 Object 사용하기
//        return redisTemplate;
//    }

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());   // Key: String
//        redisTemplate.setValueSerializer(objectMapper);  // Value: 직렬화에 사용할 Object 사용하기
//        return redisTemplate;
//    }

    @Bean
    public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

//    @Bean
//    public GenericJackson2JsonRedisSerializer jsonRedisSerializer(Jackson2ObjectMapperBuilder objectMapperBuilder) {
//        var objectMapper = objectMapperBuilder.build();
//        GenericJackson2JsonRedisSerializer.registerNullValueSerializer(objectMapper, null);
////        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
////                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//        return new GenericJackson2JsonRedisSerializer(objectMapper);
//    }

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(
//            RedisConnectionFactory redisConnectionFactory,
//            GenericJackson2JsonRedisSerializer jsonRedisSerializer) {
//        var template = new RedisTemplate<String, Object>();
//        template.setConnectionFactory(redisConnectionFactory);
//        template.setKeySerializer(RedisSerializer.string());
//        template.setValueSerializer(jsonRedisSerializer);
//        template.setHashKeySerializer(RedisSerializer.string());
//        template.setHashValueSerializer(jsonRedisSerializer);
//        return template;
//    }
}
