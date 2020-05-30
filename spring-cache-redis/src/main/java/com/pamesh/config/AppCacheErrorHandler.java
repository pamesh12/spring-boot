package com.pamesh.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.lang.Nullable;

/**
 * The Class AppCacheErrorHandler.
 *
 * @author Pamesh Bansal
 */
public class AppCacheErrorHandler extends SimpleCacheErrorHandler {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AppCacheErrorHandler.class);
	
	/**
	 * Handle cache get error.
	 *
	 * @param exception the exception
	 * @param cache the cache
	 * @param key the key
	 */
	@Override
	public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
		LOGGER.error("Error in cache get operation ", exception);
	}
	
	
	/**
	 * Handle cache put error.
	 *
	 * @param exception the exception
	 * @param cache the cache
	 * @param key the key
	 * @param value the value
	 */
	@Override
	public void handleCachePutError(RuntimeException exception, Cache cache, Object key, @Nullable Object value) {
		LOGGER.error("Error in cache put operation ", exception);
	}

	/**
	 * Handle cache evict error.
	 *
	 * @param exception the exception
	 * @param cache the cache
	 * @param key the key
	 */
	@Override
	public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
		LOGGER.error("Error in cache evict operation ", exception);
	}

	/**
	 * Handle cache clear error.
	 *
	 * @param exception the exception
	 * @param cache the cache
	 */
	@Override
	public void handleCacheClearError(RuntimeException exception, Cache cache) {
		LOGGER.error("Error in cache clear operation ", exception);
	}
}
