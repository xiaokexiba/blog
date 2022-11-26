package com.yeff.blog.service;

import java.util.Map;

/**
 * Redis业务层接口
 *
 * @author xoke
 * @date 2022/11/24
 */
public interface RedisService {

    /**
     * 保存属性
     *
     * @param key   key值
     * @param value value值
     * @param time  过期时间
     */
    void set(String key, Object value, Long time);

    /**
     * 保存属性
     *
     * @param key   key值
     * @param value value值
     */
    void set(String key, Object value);

    /**
     * 获取属性
     *
     * @param key key值
     * @return 返回对象
     */
    Object get(String key);

    /**
     * 设置过期时间
     *
     * @param key  key
     * @param time 过期时间
     * @return 操作结果
     */
    Boolean expire(String key, Long time);

    /**
     * 直接设置整个Hash结构
     *
     * @param key 外部key
     * @param map hashMap值
     */
    void hSetAll(String key, Map<String, ?> map);
}
