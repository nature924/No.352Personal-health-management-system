package com.jyx.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Configuration
public class MyRedisConfig {

    // 自动注入 RedisConnectionFactory 实例
    @Resource
    private RedisConnectionFactory factory;

    @Bean
    public RedisTemplate redisTemplate(){
        // 创建 RedisTemplate 实例
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
        // 设置 RedisConnectionFactory 实例
        redisTemplate.setConnectionFactory(factory);
        // 设置 RedisTemplate 的 key 序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 创建 Jackson2JsonRedisSerializer 实例
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // 设置 RedisTemplate 的 value 序列化方式
        redisTemplate.setValueSerializer(serializer);
        // 创建 ObjectMapper 实例
        ObjectMapper om = new ObjectMapper();
        // 设置所有访问权限可见
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 设置日期格式
        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 设置时区
        om.setTimeZone(TimeZone.getDefault());
        // 禁用使用注解
        om.configure(MapperFeature.USE_ANNOTATIONS, false);
        // 禁止在遇到未知属性时抛出异常
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 禁止序列化空对象
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 启用默认类型
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance ,ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        // 设置序列化时包含非空属性
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 设置 Jackson2JsonRedisSerializer 的 ObjectMapper 实例
        serializer.setObjectMapper(om);

        // 返回 RedisTemplate 实例
        return redisTemplate;
    }
}
