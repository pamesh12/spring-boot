package com.pamesh.config;

import java.io.File;
import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ResourceUtils;

@Configuration
@ConditionalOnProperty(prefix = "spring.cache", name = "type", havingValue = "redis", matchIfMissing = true)
public class RedisCacheConfig {

	/** The redisson. */
	private RedissonClient redisson;

	/** The active profile. */
	@Value("${spring.profiles.active}")
	private String activeProfile;

	/**
	 * Redisson client.
	 *
	 * @return the redisson client
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Bean(destroyMethod = "shutdown")
	public RedissonClient redissonClient() throws IOException {
		String configFileName = "redis-" + activeProfile + ".yml";
		File resourceURL = ResourceUtils.getFile("classpath:" + configFileName);
		Config config = Config.fromYAML(resourceURL);
		redisson = Redisson.create(config);
		return redisson;
	}

	/**
	 * Jedis connection factory.
	 *
	 * @param redissonClient the redisson client
	 * @return the redis connection factory
	 */
	@Bean
	public RedisConnectionFactory redissonConnectionFactory(RedissonClient redissonClient) {
		return new RedissonConnectionFactory(redissonClient);
	}

	/**
	 * Redis template.
	 *
	 * @param redissonConnectionFactory the redisson connection factory
	 * @return the redis template
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redissonConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redissonConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		return template;
	}

	@Bean
	public CacheManager cacheManager(RedissonClient redissonClient) throws IOException {
		String configFileName = "cache-config-" + activeProfile + ".yml";
		return new RedissonSpringCacheManager(redissonClient, "classpath:/" + configFileName);
	}

}
