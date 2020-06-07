package com.pamesh.rest.config;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.pamesh.rest.advice.AsyncExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class AsyncConfig.
 *
 * @author Pamesh Bansal
 */
@EnableAsync
@Configuration
@Slf4j
public class AsyncConfig implements AsyncConfigurer {

	/** The application properties. */
	private final ApplicationProperties applicationProperties;

	/**
	 * Instantiates a new async config.
	 *
	 * @param applicationProperties the application properties
	 */
	public AsyncConfig(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	/* (non-Javadoc)
	 * @see org.springframework.scheduling.annotation.AsyncConfigurer#getAsyncExecutor()
	 */
	@Bean(name = "threadPoolTaskExecutor")
	@Override
	public Executor getAsyncExecutor() {
		log.info("Creating Async Task Executor");
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(applicationProperties.getAsync().getCorePoolSize());
		executor.setMaxPoolSize(applicationProperties.getAsync().getMaxPoolSize());
		executor.setQueueCapacity(applicationProperties.getAsync().getQueueCapacity());
		executor.setThreadNamePrefix("Executor-");
		log.info("Async Task Executor created. CorePoolSize {}, maxPoolSize {}, QueueCapacity {}", 
				executor.getCorePoolSize(), executor.getMaxPoolSize(),applicationProperties.getAsync().getQueueCapacity());
		return executor;
	}

	/* (non-Javadoc)
	 * @see org.springframework.scheduling.annotation.AsyncConfigurer#getAsyncUncaughtExceptionHandler()
	 */
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return asyncExceptionHandler();
	}
	
	/**
	 * Async exception handler.
	 *
	 * @return the async exception handler
	 */
	@Bean
	public AsyncExceptionHandler asyncExceptionHandler() {
		return new AsyncExceptionHandler();
	}
}
