package com.challenge.services.infrastructure.input.adapter.rest.config;


import com.challenge.services.application.input.port.AccountService;
import com.challenge.services.application.service.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
@EnableRedisRepositories
public class RedisConfig {
    private final AccountService accountService;

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("customer");
    }

//    @Bean(name = "redisConnectionFactory")
//    public JedisConnectionFactory redisConnectionFactory() {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.setHostName("redis-19515.c13.us-east-1-3.ec2.redns.redis-cloud.com");
//        jedisConnectionFactory.setPort(19515);
//        jedisConnectionFactory.setPassword("gym9SLXh3sVjBemabw2opFpbFHapmqDt");
//        return jedisConnectionFactory;
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> template() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory());
//        return template;
//    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory("redis-19515.c13.us-east-1-3.ec2.redns.redis-cloud.com", 19515);
        lettuceConnectionFactory.setPassword("gym9SLXh3sVjBemabw2opFpbFHapmqDt"); // Set password if required
        return lettuceConnectionFactory;
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(new RedisSubscriber(accountService), "onMessage");
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(messageListenerAdapter(), topic());
        return container;
    }
}