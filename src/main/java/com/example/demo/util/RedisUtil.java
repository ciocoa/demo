package com.example.demo.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;


    /**
     * 有效时间
     *
     * @param key     Redis 键
     * @param timeout 超时时间
     * @param unit    时间单位
     */
    public void expire(String key, Long timeout, TimeUnit unit) {
        if (unit == null) {
            unit = TimeUnit.SECONDS;
        }
        redisTemplate.expire(key, timeout, unit);
    }

    /**
     * Key 是否存在
     *
     * @param key Redis 键
     * @return Boolean
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除 Redis 缓存
     *
     * @param key Redis 键
     */
    public void delRedisCache(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除 Redis 缓存 - 多个
     *
     * @param collection Redis 键集合
     * @return Long
     */
    public Long delRedisCaches(Collection<String> collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 设置 Redis 缓存 - OBJECT
     *
     * @param key   Redis 键
     * @param value Redis 值
     */
    public <T> void setCacheObject(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取 Redis 缓存 - OBJECT
     *
     * @param key Redis 键
     * @return T
     */
    public Object getCacheObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置 Redis 缓存 - LIST
     *
     * @param key   Redis 键
     * @param value Redis 值 List
     * @return Long
     */
    public <T> Long setCacheList(String key, List<T> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 获取 Redis 缓存 - LIST
     *
     * @param key Redis 键
     * @return List
     */
    public List<Object> getCacheList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 设置 Redis 缓存 - SET
     *
     * @param key   Redis 键
     * @param value Redis 值 Set
     * @return Object
     */
    public <T> BoundSetOperations<String, Object> setCacheSet(String key, Set<T> value) {
        BoundSetOperations<String, Object> operations = redisTemplate.boundSetOps(key);
        for (T t : value) operations.add(t);
        return operations;
    }

    /**
     * 获取 Redis 缓存 - SET
     *
     * @param key Redis 键
     * @return Set
     */
    public Set<Object> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 设置 Redis 缓存 - MAP
     *
     * @param key   Redis 键
     * @param value Redis 值 Map
     */
    public <T> void setCacheMap(String key, Map<String, T> value) {
        if (value != null) {
            redisTemplate.opsForHash().putAll(key, value);
        }
    }

    /**
     * 获取 Redis 缓存 - MAP
     *
     * @param key Redis 键
     * @return Map
     */
    public Map<Object, Object> getCacheMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 设置 Redis 缓存 - HASH
     *
     * @param key   Redis 键
     * @param value Redis 值 Hash
     */
    public <T> void setCacheHash(String key, String hKey, T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取 Redis 缓存 - HASH
     *
     * @param key Redis 键
     * @return Hash
     */
    public Object getCacheHash(String key, String hKey) {
        return redisTemplate.opsForHash().get(key, hKey);
    }

}
