package com.challenge.services.infrastructure.input.adapter.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("customer");
    }

    @Bean(name = "redisConnectionFactory")
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("redis-19515.c13.us-east-1-3.ec2.redns.redis-cloud.com");
        jedisConnectionFactory.setPort(19515);
        jedisConnectionFactory.setPassword("gym9SLXh3sVjBemabw2opFpbFHapmqDt");
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> template() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
}