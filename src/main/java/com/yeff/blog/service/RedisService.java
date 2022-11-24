package com.yeff.blog.service;

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
    void set(String key, Object value, long time);

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
}
