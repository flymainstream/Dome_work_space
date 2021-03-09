package com.js.spring.data.redis;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * @author 刘锦涛
 * @title: RedisDome
 * @projectName java_dome_work_space
 * @date 2021/3/9
 * @dateTime 18:55
 * @description: TODO
 */
public class RedisDome {

    private RedisTemplate<String, String> redisTemplate;


    public RedisDome() {
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
        redisConfiguration.setPort(6379);
        redisConfiguration.setHostName("192.168.79.128");
//        redisConfiguration.setUsername();
        redisConfiguration.setPassword("redis");
//        redisConfiguration.setDatabase();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisConfiguration);
        jedisConnectionFactory.setUsePool(true);


        this.redisTemplate = getRedisTemplate(jedisConnectionFactory);
        this.redisTemplate.afterPropertiesSet();
    }

    private RedisTemplate<String, String> getRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }

    public static void main(String[] args) {

        RedisDome redisDome = new RedisDome();

//        new
        RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
        BoundValueOperations<String, String> once = redisDome.getRedisTemplate().boundValueOps("once");
        System.out.println(
                once.get()
        );
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

}
