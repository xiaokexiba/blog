package com.yeff.blog.service.impl;

import com.yeff.blog.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis业务层接口实现类
 *
 * @author xoke
 * @date 2022/11/26
 */
@Service
public class RedisServiceImpl implements RedisService {


    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 保存属性
     *
     * @param key   key值
     * @param value value值
     * @param time  过期时间
     */
    @Override
    public void set(String key, Object value, Long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
    }

    /**
     * 保存属性
     *
     * @param key   key值
     * @param value value值
     */
    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取属性
     *
     * @param key key值
     * @return 返回对象
     */
    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置过期时间
     *
     * @param key  key
     * @param time 过期时间
     * @return 操作结果
     */
    @Override
    public Boolean expire(String key, Long time) {
        return redisTemplate.expire(key, time, TimeUnit.MINUTES);
    }

    /**
     * 直接设置整个Hash结构
     *
     * @param key 外部key
     * @param map hashMap值
     */
    @Override
    public void hSetAll(String key, Map<String, ?> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }
}
