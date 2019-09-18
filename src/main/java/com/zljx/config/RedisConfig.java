package com.zljx.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;


/**
 * @Author: sunzhihan
 * @Date：2019/07/25 16:32
 * @Version 1.0
 **/
@Configuration
public class RedisConfig {

    /*@Bean
    public void poolConfig(JedisPoolConfig poolConfig){
        poolConfig.
    }*/

    @Bean("jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory config = new JedisConnectionFactory();
        config.setHostName("106.52.88.123");
        config.setPort(7001);
        return config;
    }

    @Bean("stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory jedisConnectionFactory){
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory);
        return template;
    }
}
