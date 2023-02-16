package com.example.demo.config;

public interface RedisLoader<K, V> {

    V load(K key);
}

