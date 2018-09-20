package com.tritonsfs.cac.sso.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * @description redis配置类
 *
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {

	@Value("${spring.redis.host}")
	private String hostName;
	
	@Value("${spring.redis.port}")
	private int port;
	
	@Value("${spring.redis.timeout}")
	private int timeout;
	
	/**
	 * redis连接配置bean
	 * @return
	 */
	@Bean
    @ConfigurationProperties(prefix="spring.redis")
    public JedisPoolConfig getRedisConfig(){  
        JedisPoolConfig config = new JedisPoolConfig();
        return config;  
    }  
    
	/**
	 * redis连接工厂bean
	 * @return
	 */
    @Bean
    @ConfigurationProperties(prefix="spring.redis")
    public JedisConnectionFactory getConnectionFactory(){
        JedisConnectionFactory factory = new JedisConnectionFactory();
        JedisPoolConfig config = getRedisConfig();  
        factory.setPoolConfig(config);  
        factory.setHostName(hostName);
        factory.setPort(port);
        factory.setTimeout(timeout);
        return factory;  
    }

        /**
         * redis操作bean
         * @return
         */
        @Bean
        @SuppressWarnings({ "rawtypes", "unchecked" })
        public RedisTemplate getRedisTemplate(){
            RedisTemplate template = new RedisTemplate();
            template.setConnectionFactory(getConnectionFactory());
            template.afterPropertiesSet();
            RedisSerializer stringSerializer = new StringRedisSerializer();
            //设置key的序列化方式
            template.setKeySerializer(stringSerializer);
            RedisUtil.setRedisTemplate(template);
            return template;
        }
}
