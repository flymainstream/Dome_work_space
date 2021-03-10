package com.js.spring.data.redis;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;

/**
 * @author 刘锦涛
 * @title: RedisDome
 * @projectName java_dome_work_space
 * @date 2021/3/9
 * @dateTime 18:55
 * @description: TODO
 */
public class RedisDome {
    /**
     * begin
     *
     * @param args
     */
    public static void main(String[] args) {

        RedisDome redisDome = new RedisDome();

//        CreateData createData = new CreateData(redisDome);
        redisDome.query(redisDome);
    }


    private void query(RedisDome redisDome) {
        queryStr(redisDome);
        queryList(redisDome);

    }

    private void queryList(RedisDome redisDome) {

        String twice = redisDome.getRedisTemplate().opsForList().leftPop("Twice");
        System.out.println(
                twice
        );
    }

    private void queryStr(RedisDome redisDome) {
        String once = redisDome.getRedisTemplate().opsForValue().get("once");
        System.out.println(
                once
        );
    }


    private RedisTemplate<String, String> redisTemplate;


    private RedisTemplate<String, String> getRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }


    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }


    public RedisDome() {
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
        redisConfiguration.setPort(6379);
        redisConfiguration.setHostName("192.168.79.128");

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisConfiguration);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxWaitMillis(3000);
        jedisPoolConfig.setMaxIdle(300);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        jedisConnectionFactory.setUsePool(true);


        this.redisTemplate = getRedisTemplate(jedisConnectionFactory);
        this.redisTemplate.afterPropertiesSet();
    }

}

class CreateData {
    public CreateData(RedisDome redisDome) {
        creatAll(redisDome);
    }


    public CreateData(RedisDome redisDome, boolean createAll) {
        if (createAll) {
            creatAll(redisDome);
        }
    }


    private void creatAll(RedisDome redisDome) {
        insertStr(redisDome);
        insertList(redisDome);
    }

    private void insertList(RedisDome redisDome) {
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        redisDome.getRedisTemplate().opsForList().rightPush("Twice", strings.toString());

    }

    private void insertStr(RedisDome redisDome) {
        redisDome.getRedisTemplate().opsForValue().set("once", "value");
    }

}
